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
        //String requiredFileds[] = {"resTime","rslt_cd","rslt_msg"};
        if (!resultMap.containsKey("resTime")){
            resultMap.put("resTime", DateUtil.getSimpleTime());
        }
        if (!resultMap.containsKey("rslt_cd")){
            resultMap.put("rslt_cd", wEnum.getCode());
        }
        if (!resultMap.containsKey("rslt_msg")){
            resultMap.put("rslt_msg", wEnum.getMessage(resultMsg, isBaseMessage));
        }
        prmMap.put("svcNm"  , "study");
        return resultMap;
    }

    public static Map<String, Object>  addPlatformInfo(ResEnum wEnum) {
        return addPlatformInfo(null, wEnum, "", false);
    }

    public static Map<String, Object> setRsltInfo(Map<String, Object> prmMap, String rsltCode, String msg) {
        prmMap.put("svcNm"    , "study");
        prmMap.put("rslt_cd"  , rsltCode);
        prmMap.put("rslt_msg" , msg);
        return prmMap;
    }

    /**
     * 플랫폼공통응답부를 생성한다
     * @return
     */
    public static Map<String, Object> createResComm() {
        return  MapBuilder.createInstance()
                          .add("svcNm"     , "study")
                          .add("rslt_cd"   , "")
                          .add("rslt_msg"  , "")
                          .toMap();
    }

}
