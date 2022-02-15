package blog.benggri.springboot.biz.usr;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsrDao {

    List<Map<String, Object>> getDataList(Map<String, Object> prmMap);
    Map<String, Object> getData(Map<String, Object> prmMap);

}
