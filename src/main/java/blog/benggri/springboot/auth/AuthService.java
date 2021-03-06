package blog.benggri.springboot.auth;

import blog.benggri.springboot.auth.vo.LoginReqVo;
import blog.benggri.springboot.auth.vo.LoginResVo;
import blog.benggri.springboot.auth.vo.SignupReqVo;
import blog.benggri.springboot.auth.vo.TokenReqVo;
import blog.benggri.springboot.jpa.entity.usr.UsrLoginEntity;
import blog.benggri.springboot.jpa.repository.cd.CdRepository;
import blog.benggri.springboot.jpa.repository.usr.UsrLoginRepository;
import blog.benggri.springboot.comm.exception.CustomException;
import blog.benggri.springboot.comm.util.MapBuilder;
import blog.benggri.springboot.config.jwt.TokenProvider;
import blog.benggri.springboot.config.jwt.TokenVo;
import blog.benggri.springboot.jpa.entity.token.TokenEntity;
import blog.benggri.springboot.jpa.entity.usr.UsrEntity;
import blog.benggri.springboot.jpa.entity.usr.UsrInfoEntity;
import blog.benggri.springboot.jpa.repository.token.TokenRepository;
import blog.benggri.springboot.jpa.repository.usr.UsrInfoRepository;
import blog.benggri.springboot.jpa.repository.usr.UsrRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static blog.benggri.springboot.comm.util.DateUtil.getSimpleDate;
import static blog.benggri.springboot.comm.util.StringUtil.STEP;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UsrRepository usrRepository;

    @Autowired
    private UsrInfoRepository usrInfoRepository;

    @Autowired
    private UsrLoginRepository usrLoginRepository;

    @Autowired
    private CdRepository cdRepository;

    @Autowired
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional(value="basicTransactionManager")
    public Map<String, Object> signup(SignupReqVo signupReqVo) {
        STEP(log, "??? ?????? ??????");
        if (usrRepository.existsByUsrId(signupReqVo.getUsrId())) {
            throw new CustomException("?????? ???????????? ?????? ???????????????");
        }

        UsrEntity usr = signupReqVo.toUsr(passwordEncoder);
        LoginResVo result = LoginResVo.of(usrRepository.save(usr));

        STEP(log, "????????? ?????? ??????");
        UsrInfoEntity usrInfo = signupReqVo.toUsrInfo();
        usrInfo.setUsrSq(usr.getUsrSq());
        usrInfo.setEncUsrEmail(cdRepository.encrypt(usrInfo.getEncUsrEmail()));
        usrInfo.setEncUsrPhnNo(cdRepository.encrypt(usrInfo.getEncUsrPhnNo()));
        usrInfoRepository.save(usrInfo);

        return MapBuilder.<String,Object>createInstance()
                         .add( "id" , result.getUsrId() )
                         .toMap();
    }

    @Transactional(value="basicTransactionManager")
    public Map<String, Object> login(LoginReqVo req) {
        STEP(log, "1. Login ID/PW ??? ???????????? AuthenticationToken ??????");
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();

        STEP(log, "2. ????????? ?????? (????????? ???????????? ??????) ??? ??????????????? ??????");
        //    authenticate ???????????? ????????? ??? ??? SvcCustomUserDetails ?????? ???????????? loadUserByUsername ???????????? ?????????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        STEP(log, "3. ?????? ????????? ???????????? JWT ?????? ??????");
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication);

        STEP(log, "4. RefreshToken ??????");
        TokenEntity refreshToken = TokenEntity.builder()
                                              .key(authentication.getName())
                                              .value(tokenVo.getRefreshToken())
                                              .build();

        tokenRepository.save(refreshToken);

        Optional<UsrEntity> usrEntity = usrRepository.findByUsrId( req.getUsrId() );
        UsrLoginEntity usrLoginEntity = UsrLoginEntity.builder().loginDt( getSimpleDate() ).usrSq( usrEntity.get().getUsrSq() ).build();
        usrLoginRepository.save(usrLoginEntity); // ????????? ????????? ??????

        STEP(log, "5. ?????? ??????");
        // 5. ?????? ??????
        return MapBuilder.createInstance()
                         .add( "usr_id"                  , req.getUsrId()                    )
                         .add( "grant_type"              , tokenVo.getGrantType()            )
                         .add( "access_token"            , tokenVo.getAccessToken()          )
                         .add( "refresh_token"           , tokenVo.getRefreshToken()         )
                         .add( "access_token_expires_in" , tokenVo.getAccessTokenExpiresIn() )
                         .toMap();
    }

    @Transactional(value="basicTransactionManager")
    public Map<String, Object> reissue(TokenReqVo voTokenReq) {
        STEP(log, "1. Refresh Token ??????");
        if (!tokenProvider.validateToken(voTokenReq.getRefreshToken())) {
            throw new CustomException("???????????? ?????? Token");
        }

        STEP(log, "2. Access Token ?????? Member ID ????????????");
        Authentication authentication = tokenProvider.getAuthentication(voTokenReq.getAccessToken());

        STEP(log, "3. ??????????????? Member ID ??? ???????????? Refresh Token ??? ?????????");
        TokenEntity tokenEntity = tokenRepository.findByK(authentication.getName())
                                                                        .orElseThrow(() -> new CustomException("??????????????? ??????????????????."));

        STEP(log, "4. Refresh Token ??????????????? ??????");
        if ( !tokenEntity.getV().equals(voTokenReq.getRefreshToken()) ) {
            throw new CustomException("????????? ?????? ????????? ???????????? ????????????.");
        }

        STEP(log, "5. ????????? ?????? ??????");
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication);

        STEP(log, "6. ????????? ?????? ?????? ????????????");
        TokenEntity newTokenEntity = tokenEntity.updateValue(tokenVo.getRefreshToken());
        tokenRepository.save(newTokenEntity);

        STEP(log, "7. ?????? ??????");

        return MapBuilder.createInstance()
                         .add( "grant_type"              , tokenVo.getGrantType()            )
                         .add( "access_token"            , tokenVo.getAccessToken()          )
                         .add( "refresh_token"           , tokenVo.getRefreshToken()         )
                         .add( "access_token_expires_in" , tokenVo.getAccessTokenExpiresIn() )
                         .toMap();
    }
}
