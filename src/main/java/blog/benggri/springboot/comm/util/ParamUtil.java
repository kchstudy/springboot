package blog.benggri.springboot.comm.util;

import blog.benggri.springboot.comm.exception.CustomException;

import java.util.Map;

public class ParamUtil {

    /**
     * 파라미터 체크 로직
     * @param prmMap : 업무 파라미터
     * @param checkList : 체크 대상 파라미터 <br/>
     * { "key:name:Y/N","key:name:Y/N",...,"key:name:Y/N" }
     */
    public static void checkParam(Map<?, ?> prmMap, String[] checkList) {
        for (String selecteStr : checkList) {
            String selectdInfo[] = new String[3]; //[0]Key, [1]설명, [2]필수여부YN
            if (selecteStr.contains(":")) {
                String[] tmpStrings = selecteStr.split(":");
                if (tmpStrings.length == 3) {
                    selectdInfo[0] = tmpStrings[0]; //Key
                    selectdInfo[1] = tmpStrings[1]; //Key Desc
                    selectdInfo[2] = tmpStrings[2]; //필수여부
                }else if (tmpStrings.length == 2) {
                    selectdInfo[0] = tmpStrings[0]; //Key
                    selectdInfo[1] = tmpStrings[1]; //Key Desc
                    selectdInfo[2] = "Y"; //필수여부
                }else if (tmpStrings.length == 1) {
                    selectdInfo[0] = tmpStrings[0]; //Key
                    selectdInfo[1] = ""; //Key Desc
                    selectdInfo[2] = "Y"; //필수여부
                }
            }else {
                selectdInfo[0] = selecteStr; //Key
                selectdInfo[1] = "";         //Key Desc
                selectdInfo[2] = "Y";        //필수여부
            }

            if (!prmMap.containsKey(selectdInfo[0]) && "Y".equals(selectdInfo[2])) {
                throw new CustomException("입력항목이 누락되었습니다[" + selecteStr + "]");
            }

            if (StringUtil.isEmptyObj(prmMap.get(selectdInfo[0])) && "Y".equals(selectdInfo[2])) {
                throw new CustomException("입력항목이 입력되지 않았습니다.[" + selecteStr + "]");
            }
        }
    }

}
