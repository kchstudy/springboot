package blog.benggri.springboot.business.usr;

import blog.benggri.springboot.comm.backup.BackUpEnum;
import blog.benggri.springboot.comm.backup.BackUpService;
import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static blog.benggri.springboot.comm.util.ParamUtil.checkParam;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsrService {

    @Autowired
    private BackUpService backUpService;

    @Autowired
    private UsrDao usrDao;

    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> getUsrInfo(Map<String, Object> prmMap) {
        String prmString[] = {"usr_sq:사용자_순번"};
        checkParam(prmMap, prmString);

        Map<String, Object> usr = usrDao.getUsr(prmMap);
        Map<String, Object> usrInfo = usrDao.getUsrInfo(prmMap);

        return MapBuilder.createInstance()
                         .add("usr"      , usr    )
                         .add("usr_info" , usrInfo)
                         .toMap();

    }

    public Map<String, Object> updateUsrInfo(Map<String, Object> prmMap) {
        String prmString[] = {"usr_sq:사용자_순번","usr_nm:사용자_이름","usr_phn_no:사용자_전화_번호","usr_email:사용자_이메일"};
        checkParam(prmMap, prmString);

        backUpService.backup(BackUpEnum.USR_INFO, prmMap, "사용자 요청:사용자_정보 변경");

        int cnt = usrDao.updateUsrInfo(prmMap);
        boolean result = ( cnt < 1 ) ? false : true;

        Map<String, Object> usr = usrDao.getUsr(prmMap);
        Map<String, Object> usrInfo = usrDao.getUsrInfo(prmMap);

        return MapBuilder.createInstance()
                         .add("result"   , result )
                         .add("usr"      , usr    )
                         .add("usr_info" , usrInfo)
                         .toMap();
    }

    public Map<String, Object> resetUsrPwd(Map<String, Object> prmMap) {
        String prmString[] = {"usr_sq:사용자_순번","usr_pwd:사용자_비밀번호","pwd_expr_dt:비밀번호_만료_일자"};
        checkParam(prmMap, prmString);

        backUpService.backup(BackUpEnum.USR, prmMap, "사용자 요청:사용자_정보 비밀번호 초기화");

        prmMap.put( "usr_pwd", passwordEncoder.encode(nvl(prmMap.get("usr_pwd"))) );
        int cnt = usrDao.resetUsrPwd(prmMap);
        boolean result = ( cnt < 1 ) ? false : true;

        Map<String, Object> usr = usrDao.getUsr(prmMap);
        Map<String, Object> usrInfo = usrDao.getUsrInfo(prmMap);

        return MapBuilder.createInstance()
                         .add("result"   , result )
                         .add("usr"      , usr    )
                         .add("usr_info" , usrInfo)
                         .toMap();
    }

}
