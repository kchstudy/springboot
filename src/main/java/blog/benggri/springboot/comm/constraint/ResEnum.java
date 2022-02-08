package blog.benggri.springboot.comm.constraint;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static blog.benggri.springboot.comm.util.StringUtil.isEmptyObj;

public enum ResEnum {

    //200 성공 
    SUCCESS(HttpStatus.OK, "0000", "정상처리되었습니다") , //200(성공)
    //400 요청오류 
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4011", "현재응답처리가 불가합니다") , //401(UNAUTHORIZED)
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "4051", "지원하지 않는 호출(Http)입니다") , //405(METHOD_NOT_ALLOWED)
    PARAM_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "4052", "지원하지 않는 파라미터(Http)입니다") , //405(METHOD_NOT_ALLOWED)
    //500 서버오류  
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "처리중 오류가 발생하였습니다") , //500(INTERNAL_SERVER_ERROR)
    SVC_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "5031", "서비스를 이용할수 없습니다.") , //503(SERVICE_UNAVAILABLE)
    ;

    @Getter
    private HttpStatus httpStatus; //HttpStatus

    @Getter
    private String code;  //업무코드

    @Getter 
    private String baseMessage; //업무코드메세지

    /**
     * 
     * @param extMessage
     * @param isBaseMessage "기본메세지여부"
     * @return
     */
    public String getMessage(String extMessage, boolean isBaseMessage) {
        if (isBaseMessage) {
            return extMessage;
        }
        return isEmptyObj(extMessage) ? baseMessage : baseMessage + "[" + extMessage + "]";
    }

    public String getMessage(String extMessage) {       
        return getMessage(extMessage, false);
    }

    /**
     * 기본메세지를 지정한다
     * @param pMessage
     * @return
     */
    public ResEnum setBaseMessage(String pMessage){
        this.baseMessage = pMessage;
        return this;
    }

    private ResEnum(HttpStatus badRequest, String pCode, String pMessage) {
        this.httpStatus = badRequest;
        this.code = pCode;
        this.baseMessage = pMessage;
    }

}
