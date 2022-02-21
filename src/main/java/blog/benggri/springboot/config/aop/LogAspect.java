package blog.benggri.springboot.config.aop;

import blog.benggri.springboot.biz.jpa.entity.log.LogEntity;
import blog.benggri.springboot.biz.jpa.repository.log.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static blog.benggri.springboot.comm.util.DateUtil.getFullTime;
import static blog.benggri.springboot.comm.util.DateUtil.getSimpleDate;
import static blog.benggri.springboot.comm.util.StringUtil.STEP;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogRepository logRepository;

    @Around("execution(* blog.benggri.springboot..*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        LogEntity logEntity = LogEntity.builder()
                                       .logDt(getSimpleDate())
                                       .cmpnnt(pjp.getSignature().getDeclaringTypeName())
                                       .method(pjp.getSignature().getName())
                                       .reqDt(getFullTime())
                                       .sttsCd("1000")
                                       .build();

        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            STEP(log, "[arg]["+arg+"]");
            sb.append(arg.toString());
        }
        logEntity.setReqCntnt(sb.toString());

        logEntity = logRepository.save(logEntity);
        STEP(log, "[logEntity]["+logEntity+"]");

        HttpServletRequest request = (HttpServletRequest) args[0];
        request.setAttribute("log_dt", logEntity.getLogDt());
        request.setAttribute("log_sq", logEntity.getLogSq());

        Object result = pjp.proceed();

        logEntity.setResDt(getFullTime());
        logEntity.setSttsCd("2000");
        logEntity.setResCntnt(result.toString());

        logRepository.save(logEntity);

        return result;
    }

    @AfterThrowing(pointcut="execution(* blog.benggri.springboot..*Controller.*(..))", throwing="ex")
    public void loggingAfterThrowing(JoinPoint jp, Throwable ex) throws Throwable {
        log.info("[ERROR][" + jp.getSignature().getDeclaringTypeName() + "][" + jp.getSignature().getName()+"]");
        Object[] args = jp.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String log_dt = nvl(request.getAttribute("log_dt"));
        Long log_sq = Long.parseLong(nvl(request.getAttribute("log_sq")));

        LogEntity logEntity = logRepository.findByLogDtAndLogSq(log_dt, log_sq);
        logEntity.setResDt(getFullTime());
        logEntity.setResCntnt(ex.toString());
        logEntity.setSttsCd("9000");
        logRepository.save(logEntity);
    }

}
