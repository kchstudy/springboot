package blog.benggri.springboot.comm.code;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodeDao {

    /**
     * 공통그룹코드 목록 정보 조회
     * @param prmMap
     * @return
     */
    List<Map<String, Object>> getGrpCdList(Map<String, Object> prmMap);

    /**
     * 공통그룹코드 정보 조회
     * @param prmMap
     * @return
     */
    Map<String, Object> getGrpCd(Map<String, Object> prmMap);

    /**
     * 공통코드 목록 정보 조회
     * @param prmMap
     * @return
     */
    List<Map<String, Object>> getCdList(Map<String, Object> prmMap);

    /**
     * 공통코드 정보 조회
     * @param prmMap
     * @return
     */
    Map<String, Object> getCd(Map<String, Object> prmMap);

}
