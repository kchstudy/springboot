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
        Object result = pjp.proceed(); // 실제 컨트롤러 로직이 수행되는 구간
        return result;
    }

}
