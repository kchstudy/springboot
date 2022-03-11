package blog.benggri.springboot.business.code.mng;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodeMngDao {

    List<Map<String, Object>> getGrpCdList(Map<String, Object> prmMap);
    Map<String, Object> getGrpCd(Map<String, Object> prmMap);
    int createGrpCd(Map<String, Object> prmMap);
    int updateGrpCd(Map<String, Object> prmMap);
    int deleteGrpCd(Map<String, Object> prmMap);

    List<Map<String, Object>> getCdList(Map<String, Object> prmMap);
    Map<String, Object> getCd(Map<String, Object> prmMap);
    int createCd(Map<String, Object> prmMap);
    int updateCd(Map<String, Object> prmMap);
    int deleteCd(Map<String, Object> prmMap);

}
