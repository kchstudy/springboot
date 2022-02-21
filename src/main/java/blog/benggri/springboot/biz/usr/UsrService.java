package blog.benggri.springboot.biz.usr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UsrService {

    @Autowired
    private UsrDao usrDao;

    public Map<String, Object> getDataList(Map<String, Object> prmMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> data = usrDao.getDataList(prmMap);
        result.put("data", data);
        return result;
    }

    public Map<String, Object> getData(Map<String, Object> prmMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = usrDao.getData(prmMap);
        result.put("data", data);
        return result;
    }

}