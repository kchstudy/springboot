package blog.benggri.springboot.business.usr;

import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static blog.benggri.springboot.comm.util.ParamUtil.checkParam;

@Slf4j
@Service
public class UsrService {

    @Autowired
    private UsrDao usrDao;

    public Map<String, Object> getUsrInfo(Map<String, Object> prmMap) {
        String prmString[] = {"usr_id:사용자_ID"};
        checkParam(prmMap, prmString);

        Map<String, Object> usr = usrDao.getUsr(prmMap);
        Map<String, Object> usrInfo = usrDao.getUsrInfo(usr);

        return MapBuilder.createInstance()
                         .add("usr"      , usr    )
                         .add("usr_info" , usrInfo)
                         .toMap();

    }

}
