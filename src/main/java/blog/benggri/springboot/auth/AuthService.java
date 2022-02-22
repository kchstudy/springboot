package blog.benggri.springboot.auth;

import blog.benggri.springboot.auth.vo.LoginReqVo;
import blog.benggri.springboot.auth.vo.LoginResVo;
import blog.benggri.springboot.auth.vo.SignupReqVo;
import blog.benggri.springboot.auth.vo.TokenReqVo;
import blog.benggri.springboot.jpa.entity.usr.UsrLoginEntity;
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
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional(value="basicTransactionManager")
    public Map<String, Object> signup(SignupReqVo signupReqVo) {
        STEP(log, "기 가입 확인");
        if (usrRepository.existsByUsrId(signupReqVo.getUsrId())) {
            throw new CustomException("이미 가입되어 있는 유저입니다");
        }

        UsrEntity usr = signupReqVo.toUsr(passwordEncoder);
        LoginResVo result = LoginResVo.of(usrRepository.save(usr));

        STEP(log, "사용자 정보 등록");
        UsrInfoEntity usrInfo = signupReqVo.toUsrInfo();
        usrInfo.setUsrSq(usr.getUsrSq());
        usrInfoRepository.save(usrInfo);

        return MapBuilder.<String,Object>createInstance()
                         .add( "id" , result.getUsrId() )
                         .toMap();
    }

    @Transactional(value="basicTransactionManager")
    public Map<String, Object> login(LoginReqVo req) {
        STEP(log, "1. Login ID/PW 를 기반으로 AuthenticationToken 생성");
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();

        STEP(log, "2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분");
        //    authenticate 메서드가 실행이 될 때 SvcCustomUserDetails 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        STEP(log, "3. 인증 정보를 기반으로 JWT 토큰 생성");
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication);

        STEP(log, "4. RefreshToken 저장");
        TokenEntity refreshToken = TokenEntity.builder()
                                              .key(authentication.getName())
                                              .value(tokenVo.getRefreshToken())
                                              .build();

        tokenRepository.save(refreshToken);

        Optional<UsrEntity> usrEntity = usrRepository.findByUsrId( req.getUsrId() );
        UsrLoginEntity usrLoginEntity = UsrLoginEntity.builder().loginDt( getSimpleDate() ).usrSq( usrEntity.get().getUsrSq() ).build();
        usrLoginRepository.save(usrLoginEntity); // 사용자 로그인 이력

        STEP(log, "5. 토큰 발급");
        // 5. 토큰 발급
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
        STEP(log, "1. Refresh Token 검증");
        if (!tokenProvider.validateToken(voTokenReq.getRefreshToken())) {
            throw new CustomException("유효하지 않은 Token");
        }

        STEP(log, "2. Access Token 에서 Member ID 가져오기");
        Authentication authentication = tokenProvider.getAuthentication(voTokenReq.getAccessToken());

        STEP(log, "3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴");
        TokenEntity tokenEntity = tokenRepository.findByK(authentication.getName())
                                                                        .orElseThrow(() -> new CustomException("로그아웃된 사용자입니다."));

        STEP(log, "4. Refresh Token 일치하는지 검사");
        if ( !tokenEntity.getV().equals(voTokenReq.getRefreshToken()) ) {
            throw new CustomException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        STEP(log, "5. 새로운 토큰 생성");
        TokenVo tokenVo = tokenProvider.generateEntityToken(authentication);

        STEP(log, "6. 저장소 토큰 정보 업데이트");
        TokenEntity newTokenEntity = tokenEntity.updateValue(tokenVo.getRefreshToken());
        tokenRepository.save(newTokenEntity);

        STEP(log, "7. 토큰 발급");

        return MapBuilder.createInstance()
                         .add( "grant_type"              , tokenVo.getGrantType()            )
                         .add( "access_token"            , tokenVo.getAccessToken()          )
                         .add( "refresh_token"           , tokenVo.getRefreshToken()         )
                         .add( "access_token_expires_in" , tokenVo.getAccessTokenExpiresIn() )
                         .toMap();
    }
}
