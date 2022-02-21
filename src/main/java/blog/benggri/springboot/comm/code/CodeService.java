package blog.benggri.springboot.comm.code;

import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static blog.benggri.springboot.comm.util.StringUtil.isEmptyObj;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Service
public class CodeService {

    @Autowired
    CodeDao codeDao; /* 공통코드 DAO */

    /**
     * 공통그룹코드 목록 정보 조회
     * @param prmMap
     * @return
     */
    public List<Map<String, Object>> getGrpCdList(Map<String, Object> prmMap){
        return codeDao.getGrpCdList(prmMap);
    }

    /**
     * 공통그룹코드 목록 정보 조회
     * @param grpCd
     * @return
     */
    public List<Map<String, Object>> getGrpCdList(String grpCd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grpCd)
                                               .toMap();
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
     * @param grpCd
     * @return
     */
    public Map<String, Object> getGrpCd(String grpCd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grpCd)
                                               .toMap();
        return codeDao.getGrpCd(prmMap);
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
     * @param grpCd
     * @return
     */
    public List<Map<String, Object>> getCdList(String grpCd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grpCd)
                                               .toMap();
        return codeDao.getCdList(prmMap);
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
     * @param grpCd
     * @param cd
     * @return
     */
    public Map<String, Object> getCd(String grpCd, String cd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grpCd)
                                               .add("cd"     , cd)
                                               .toMap();
        return codeDao.getCd(prmMap);
    }

    /**
     * 공통코드 코드값 정보 조회
     * @param prmMap
     * @return
     */
    public String getCdVal(Map<String, Object> prmMap){
        Map<String, Object> code = codeDao.getCd(prmMap);
        if ( isEmptyObj(code) ) {
            return "";
        }
        return nvl(code.get("cd_v"));
    }

    /**
     * 공통코드 코드값 정보 조회
     * @param grpCd
     * @param cd
     * @return
     */
    public String getCdVal(String grpCd, String cd){
        Map<String, Object> prmMap = MapBuilder.createInstance()
                                               .add("grp_cd" , grpCd)
                                               .add("cd"     , cd)
                                               .toMap();
        Map<String, Object> code = codeDao.getCd(prmMap);
        if ( isEmptyObj(code) ) {
            return "";
        }
        return nvl(code.get("cd_v"));
    }

}
