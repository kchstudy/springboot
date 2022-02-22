package blog.benggri.springboot.comm.code;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodeDao {

    List<Map<String, Object>> getGrpCdList(Map<String, Object> prmMap);
    Map<String, Object> getGrpCd(Map<String, Object> prmMap);

    List<Map<String, Object>> getCdList(Map<String, Object> prmMap);
    Map<String, Object> getCd(Map<String, Object> prmMap);


}
