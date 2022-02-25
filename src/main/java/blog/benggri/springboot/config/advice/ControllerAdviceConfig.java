package blog.benggri.springboot.config.advice;

import blog.benggri.springboot.comm.constraint.ResEntity;
import blog.benggri.springboot.comm.constraint.ResEnum;
import blog.benggri.springboot.comm.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@ControllerAdvice(basePackages="blog.benggri.springboot")
public class ControllerAdviceConfig {

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        STEP(log, Level.ERROR, "handleMethodArgumentNotValidException:"+e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getBindingResult().toString());
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Map<String, Object>> handleBindException(BindException e) {
        STEP(log, Level.ERROR, "handleBindException:"+e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getBindingResult().toString());
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        STEP(log, Level.ERROR, "handleMethodArgumentTypeMismatchException:"+e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        STEP(log, Level.ERROR, "handleHttpRequestMethodNotSupportedException:"+e);
        ResEnum responseInfo = ResEnum.UNAUTHORIZED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException e) {
        STEP(log, Level.ERROR, "handleAccessDeniedException:"+e);
        ResEnum responseInfo = ResEnum.UNAUTHORIZED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Map<String, Object>> handleBusinessException(final CustomException e) {
        STEP(log, Level.ERROR, "CustomException:"+e);
        ResEnum responseInfo = ResEnum.SVC_ERROR;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getRslt_msg());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> handleException(Exception e) {
        STEP(log, Level.ERROR, "handleException:"+e);
        ResEnum responseInfo = ResEnum.ERROR;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Map<String, Object>> authenticationException(AuthenticationException e) {
        STEP(log, Level.ERROR, "authenticationException:"+e);
        ResEnum responseInfo = ResEnum.UNAUTHORIZED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

}
