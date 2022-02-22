package blog.benggri.springboot.comm.code;

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
public class CodeService {

    @Autowired
    private CodeDao codeDao;

    /**
     * 공통그룹코드 목록 정보 조회
     * @param prmMap
     * @return
     */
    public List<Map<String, Object>> getGrpCdList(Map<String, Object> prmMap){
        return codeDao.getGrpCdList(prmMap);
    }

    /**
     * 공통그룹코드 정보 조회
     * @param prmMap
     * @return
     */
    public Map<String, Object> getGrpCd(Map<String, Object> prmMap){
        return codeDao.getGrpCd(prmMap);
    }

    /**
     * 공통그룹코드 정보 조회
     * @param grp_cd
     * @return
     */
    public Map<String, Object> getGrpCd(String grp_cd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grp_cd)
                                               .toMap();
        return getGrpCd(prmMap);
    }

    /**
     * 공통코드 목록 정보 조회
     * @param prmMap
     * @return
     */
    public List<Map<String, Object>> getCdList(Map<String, Object> prmMap){
        return codeDao.getCdList(prmMap);
    }

    /**
     * 공통코드 목록 정보 조회
     * @param grp_cd
     * @return
     */
    public List<Map<String, Object>> getCdList(String grp_cd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grp_cd)
                                               .toMap();
        return getCdList(prmMap);
    }

    /**
     * 공통코드 정보 조회
     * @param prmMap
     * @return
     */
    public Map<String, Object> getCd(Map<String, Object> prmMap){
        return codeDao.getCd(prmMap);
    }

    /**
     * 공통코드 정보 조회
     * @param grp_cd
     * @param cd
     * @return
     */
    public Map<String, Object> getCd(String grp_cd, String cd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grp_cd)
                                               .add("cd"     , cd)
                                               .toMap();
        return getCd(prmMap);
    }

    /**
     * 공통코드 코드값 정보 조회
     * @param prmMap
     * @return
     */
    public String getCdVal(Map<String, Object> prmMap){
        String prmString[] = {"grp_cd:그룹_코드", "cd:코드"};
        checkParam(prmMap, prmString);

        Map<String, Object> code = getCd(prmMap);
        if ( isEmptyObj(code) ) {
            return "";
        }
        return nvl(code.get("cd_v"));
    }

    /**
     * 공통코드 코드값 정보 조회
     * @param grp_cd
     * @param cd
     * @return
     */
    public String getCdVal(String grp_cd, String cd){
        return getCdVal(MapBuilder.createInstance()
                                  .add("grp_cd" , grp_cd)
                                  .add("cd"     , cd)
                                  .toMap());
    }

}
