package blog.benggri.springboot.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(* blog.benggri.springboot..*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        log.info("[START][" + pjp.getSignature().getDeclaringTypeName() + "][" + pjp.getSignature().getName()+"]");
        Object result = pjp.proceed();
        log.info("[FINISHED][" + pjp.getSignature().getDeclaringTypeName() + "][" + pjp.getSignature().getName()+"]");
        return result;
    }

    @AfterThrowing(pointcut="execution(* blog.benggri.springboot..*Controller.*(..))", throwing="ex")
    public void loggingAfterThrowing(JoinPoint jp, Throwable ex) throws Throwable {
        log.info("[ERROR][" + jp.getSignature().getDeclaringTypeName() + "][" + jp.getSignature().getName()+"]");
    }

}
