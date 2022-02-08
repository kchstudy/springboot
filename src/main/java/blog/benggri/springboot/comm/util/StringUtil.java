package blog.benggri.springboot.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class StringUtil {

    public static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 빈객체 확인
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyObj(Object obj){
        if (obj == null){
            return true;
        }

        if (obj instanceof List){
            if (((List) obj).size() == 0){
                return true;
            }
        }

        if (obj instanceof Map){
            if (((Map) obj).isEmpty()){
                return true;
            }
        }

        return "".equals(obj.toString());
    }

    /**
     * 빈항목 치환용(디폴트값 지정)
     * @param obj
     * @param defStr
     * @return
     */
    public static String nvl(Object obj, String defStr){
        if (obj == null) {
            return defStr;
        }

        if ("".equals(obj.toString().trim())) {
            return defStr;
        }

        return obj.toString();
    }

    /**
     * 빈항목 치환
     * @param obj
     * @return
     */
    public static String nvl(Object obj) {
        return nvl(obj, "");
    }
    
    /**
     * 진행단계표시
     * @param str
     */
    public static void STEP(String str) {
        log.debug("STEP[" + str + "]");
    }


    public static void STEP(Object cls, String str) {
        log.debug("Class["+cls.getClass().getSimpleName()+"]STEP[" + str + "]");
    }

    /**
     * 진행단계표시
     * @param str
     */
    public static void STEP(Logger logger, String str) {
        STEP(logger, Level.DEBUG, str);
    }

    /**
     * 진행단계표시
     * @param str
     */
    public static void STEP(Logger logger, Level lev, String str) {
        if ( "ERROR".equals(lev.toString()) ) {
            logger.error(str);
        } else if ( "WARN".equals(lev.toString()) ) {
            logger.warn(str);
        } else if ( "INFO".equals(lev.toString()) ) {
            logger.info(str);
        } else if ( "DEBUG".equals(lev.toString()) ) {
            logger.debug(str);
        } else if ( "TRACE".equals(lev.toString()) ) {
            logger.trace(str);
        }
    }

    /**
     * utf8을 지정한 Charset으로 변경
     * @param charset 변경대상이 되는 문자인코딩셋 (example : ksc-5601)
     * @param b
     * @return
     */
    public static byte[] utf8ToCharset(String charset, byte[] b){
        String str = "";
        try {
            String tmp = new String(b, "UTF-8");
            str = new String(tmp.getBytes(charset), charset);
            return str.getBytes(charset);
        }catch (Exception e) {
            str = new String(b);
            return str.getBytes();
        }

    }    
    
    /**
     * byte를 16진수 문자열로 출력
     * @param data
     * @return
     */
    public static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    /**
     * 현재 서버 IP확인 
     * @return
     */
    public static String getSvrIp() {
        InetAddress local;
        try {
            local = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "XXX.XXX.XXX.XXX";
        } 
        return local.getHostAddress();
    }

    /**
     * 숫자 천단위 콤마
     * @param num
     * @return
     */
    public static String printComma( String num ) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(Long.parseLong( num ));
    }

    /**
     * 문자 반복
     * @param param : 반복 문자
     * @param cnt   : 횟수
     * @return
     */
    public static String repeatStr( String param, int cnt ) {
        return new String(new char[cnt]).replace("\0", param);
    }

    /**
     * Left Padding
     * @param str    : 원본문자
     * @param padStr : 반복 문자
     * @param totLen : 전체 문자 길이
     * @return
     */
    public static String lPadding( String str, String padStr, int totLen ) {
        int cnt = totLen - str.length(); // 반복 횟수
        if( cnt < 0 ) {
            return str;
        }
        return repeatStr( padStr, cnt ) + str;
    }

    /**
     * Right Padding
     * @param str    : 원본문자
     * @param padStr : 반복 문자
     * @param totLen : 전체 문자 길이
     * @return
     */
    public static String rPadding( String str, String padStr, int totLen ) {
        int cnt = totLen - str.length(); // 반복 횟수
        if( cnt < 0 ) {
            return str;
        }
        return str + repeatStr( padStr, cnt );
    }
    
}
