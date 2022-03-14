package blog.benggri.springboot.business.code.mng;

import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static blog.benggri.springboot.comm.util.ParamUtil.checkParam;
import static blog.benggri.springboot.comm.util.StringUtil.isEmptyObj;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Service
public class CodeMngService {

    @Autowired
    private CodeMngDao codeMngDao;

    public Map<String, Object> getGrpCdList(Map<String, Object> prmMap) {
        String prmString[] = {"page:paging 처리 객체"};
        checkParam(prmMap, prmString);
        List<Map<String, Object>> data = codeMngDao.getGrpCdList(prmMap);
        String tot_cnt = "0";
        if ( !isEmptyObj(data) ) {
            tot_cnt = nvl(data.get(0).get("tot_cnt"), "0");
        }
        return MapBuilder.createInstance()
                         .add("data"   , data   )
                         .add("tot_cnt", tot_cnt)
                         .toMap();
    }

    public Map<String, Object> getGrpCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.getGrpCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> createGrpCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","grp_nm:그룹_명","prnt_grp_cd:상위_그룹_코드","use_yn:사용_여부"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.createGrpCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> updateGrpCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","grp_nm:그룹_명","prnt_grp_cd:상위_그룹_코드","use_yn:사용_여부"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.updateGrpCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> deleteGrpCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.deleteGrpCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> getCdList(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","page:paging 처리 객체"};
        checkParam(prmMap, prmString);
        List<Map<String, Object>> data = codeMngDao.getCdList(prmMap);
        String tot_cnt = "0";
        if ( !isEmptyObj(data) ) {
            tot_cnt = nvl(data.get(0).get("tot_cnt"), "0");
        }
        return MapBuilder.createInstance()
                         .add("data"   , data   )
                         .add("tot_cnt", tot_cnt)
                         .toMap();
    }

    public Map<String, Object> getCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","cd:코드"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.getCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> createCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","cd:코드","cd_nm:코드_명","cd_v:코드_값","note:비고","sq:순번","use_yn:사용_여부"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.createCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> updateCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","cd:코드","cd_nm:코드_명","cd_v:코드_값","note:비고","sq:순번","use_yn:사용_여부"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.updateCd(prmMap))
                         .toMap();
    }

    public Map<String, Object> deleteCd(Map<String, Object> prmMap) {
        String prmString[] = {"grp_cd:그룹_코드","cd:코드"};
        checkParam(prmMap, prmString);
        return MapBuilder.createInstance()
                         .add("data", codeMngDao.deleteCd(prmMap))
                         .toMap();
    }

}
