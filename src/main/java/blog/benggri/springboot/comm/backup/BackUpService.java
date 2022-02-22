package blog.benggri.springboot.comm.backup;

import blog.benggri.springboot.comm.exception.CustomException;
import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

import static blog.benggri.springboot.comm.util.ParamUtil.checkParam;
import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@Service
public class BackUpService {

    @Autowired
    private BackUpDao backUpDao;

    public Map<String, Object> backup(BackUpEnum target, Map<String, Object> prmMap, String note ) {
        String[] pks = target.getPks();
        checkParam(prmMap, pks);

        STEP(log, "[TABLE BACKUP]["+target.name()+"]["+target.getTbNm()+"]::START");
        Map<String, Object> result =procBackUpFn(target, note).apply(prmMap);
        STEP(log, "[TABLE BACKUP]["+target.name()+"]["+target.getTbNm()+"]::END");
        return result;
    }

    private Function<Map<String, Object>, Map<String, Object>> procBackUpFn(BackUpEnum target, String note) {
        Function<Map<String, Object>, Map<String, Object>> result = prmMap -> {
            prmMap.put("hist_note", note);

            int res = 0;
            switch (target) {
                case API:
                    res = backUpDao.backupApi(prmMap);
                    break;
                case BAT:
                    res = backUpDao.backupBat(prmMap);
                    break;
                case USR:
                    res = backUpDao.backupUsr(prmMap);
                    break;
                case USR_INFO:
                    res = backUpDao.backupUsrInfo(prmMap);
                    break;
                default:
                    new CustomException("백업을 지원하지 않는 테이블입니다.[" + target.name() + "]");
                    break;
            }

            return MapBuilder.createInstance(prmMap)
                    .add("cnt", res)
                    .toMap();
        };
        return result;
    }
}
