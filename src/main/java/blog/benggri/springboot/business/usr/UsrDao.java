package blog.benggri.springboot.business.usr;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UsrDao {

    Map<String, Object> getUsr(Map<String, Object> prmMap);
    Map<String, Object> getUsrInfo(Map<String, Object> prmMap);

}
