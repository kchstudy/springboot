package blog.benggri.springboot.view;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ViewDao {

    Map<String, Object> getUsrMenuInfo(Map<String, Object> prmMap);
    int createUsrMenuUseH(Map<String, Object> prmMap);

}
