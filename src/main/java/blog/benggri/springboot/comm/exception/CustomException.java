package blog.benggri.springboot.comm.exception;

import lombok.Getter;
import lombok.Setter;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 264887350921870905L;

    @Getter @Setter
    private String rslt_msg; //응답메세지 

    public CustomException() {
        super();
        this.rslt_msg = "서버에서 장애가 발생하였습니다.<br />잠시 후 다시 시도해주세요.";
    }

    public CustomException(Exception e) {
        super(e);
        this.rslt_msg = "서버에서 장애가 발생하였습니다.<br />잠시 후 다시 시도해주세요.";
    }

    public CustomException(String bizErrMsg) {
        super(bizErrMsg);
        this.rslt_msg = bizErrMsg;
    }

    public CustomException(Exception e, String bizErrMsg) {
        super(bizErrMsg, e);
        this.rslt_msg = bizErrMsg;
    }

}
