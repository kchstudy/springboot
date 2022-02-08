package blog.benggri.springboot.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(* blog.benggri.springboot..*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        log.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        Object result = pjp.proceed();
        log.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        return result;
    }

}
