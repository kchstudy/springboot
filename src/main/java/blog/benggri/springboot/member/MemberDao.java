package blog.benggri.springboot.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberDao {

    List<Map<String, Object>> getDataList(Map<String, Object> prmMap);
    Map<String, Object> getData(Map<String, Object> prmMap);

}
