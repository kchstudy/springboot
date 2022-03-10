package blog.benggri.springboot.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@Aspect
@Component
public class CheckAuthAspect {

    @Around("execution(* blog.benggri.springboot.view.ViewController.view(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        HttpServletRequest request = (HttpServletRequest) args[0];
        String token  = (String) args[1];
        String menuId = (String) args[2];
        STEP(log, "token:"+token   );
        STEP(log, "menuId:"+menuId );
        // TODO token 값을 이용한 jwt 검증 및 사용자 id 조회
        // TODO 사용자 id 를 이용한 menuId 권한 검증
        Object result = pjp.proceed(); // 실제 컨트롤러 로직이 수행되는 구간
        return result;
    }

}
