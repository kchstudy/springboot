package blog.benggri.springboot.comm.util;

import blog.benggri.springboot.comm.constraint.ResEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    /**
     * 공통 응답정보를 추가한
     * @param prmMap
     * @param wEnum
     * @return
     */
    public static Map<String, Object>  addPlatformInfo(Map<String, Object> prmMap, ResEnum wEnum, String resultMsg, boolean isBaseMessage) {
        Map<String, Object> resultMap = (prmMap == null) ? new HashMap<String,Object>() : prmMap;
        //String requiredFileds[] = {"resTime","resCode","resMsg"};
        if (!resultMap.containsKey("resTime")){
            resultMap.put("resTime", DateUtil.getSimpleTime());
        }
        if (!resultMap.containsKey("resCode")){
            resultMap.put("resCode", wEnum.getCode());
        }
        if (!resultMap.containsKey("resMsg")){
            resultMap.put("resMsg", wEnum.getMessage(resultMsg, isBaseMessage));
        }
        prmMap.put("svcNm"  , "system");
        return resultMap;
    }

    public static Map<String, Object>  addPlatformInfo(ResEnum wEnum) {
        return addPlatformInfo(null, wEnum, "", false);
    }

    public static Map<String, Object> setRsltInfo(Map<String, Object> prmMap, String rsltCode, String msg) {
        prmMap.put("svcNm"  , "system");
        prmMap.put("resCode", rsltCode);
        prmMap.put("resMsg" , msg);
        return prmMap;
    }

    /**
     * 플랫폼공통응답부를 생성한다
     * @return
     */
    public static Map<String, Object> createResComm() {
        return  MapBuilder.<String, Object>createInstance()
                .add("resCode" , "")
                .add("resMsg"  , "")
                .toMap();
    }

}
