package blog.benggri.springboot.comm.constraint;

import blog.benggri.springboot.comm.util.ResponseUtil;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResEntity <T extends Map<String, Object>> extends ResponseEntity<T> {

    private T resMap;

    public ResEntity(T body, ResEnum wEnum, String resultMsg, boolean isBaseMessage) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.addPlatformInfo(resMap, wEnum, resultMsg, isBaseMessage);
    }
    
    public ResEntity(T body, ResEnum wEnum, String resultMsg) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.addPlatformInfo(resMap, wEnum, resultMsg, false);
    }
    
    public ResEntity(T body, ResEnum wEnum) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.addPlatformInfo(resMap, wEnum, "", false);
    }
}
