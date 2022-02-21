package blog.benggri.springboot.config.aop;

import blog.benggri.springboot.jpa.entity.svclog.SvcLogEntity;
import blog.benggri.springboot.jpa.repository.svclog.SvcLogRepository;
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
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SvcLogRepository svcLogRepository;

    @Around("execution(* blog.benggri.springboot..*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        SvcLogEntity svcLogEntity = SvcLogEntity.builder()
                                       .logDt(getSimpleDate())
                                       .cmpnnt(pjp.getTarget().getClass().getSimpleName())
                                       .method(pjp.getSignature().getName())
                                       .reqDt(getFullTime())
                                       .sttsCd("1000")
                                       .build();

        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        svcLogEntity.setReqCntnt(sb.toString());

        svcLogEntity = svcLogRepository.save(svcLogEntity);

        HttpServletRequest request = (HttpServletRequest) args[0];
        request.setAttribute("log_dt", svcLogEntity.getLogDt());
        request.setAttribute("log_sq", svcLogEntity.getLogSq());

        Object result = pjp.proceed(); // 실제 컨트롤러 로직이 수행되는 구간

        svcLogEntity.setResDt(getFullTime());
        svcLogEntity.setSttsCd("2000");
        svcLogEntity.setResCntnt(result.toString());

        svcLogRepository.save(svcLogEntity);

        return result;
    }

    @AfterThrowing(pointcut="execution(* blog.benggri.springboot..*Controller.*(..))", throwing="ex")
    public void loggingAfterThrowing(JoinPoint jp, Throwable ex) throws Throwable {
        Object[] args = jp.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String log_dt = nvl(request.getAttribute("log_dt"));
        Long log_sq = Long.parseLong(nvl(request.getAttribute("log_sq")));

        SvcLogEntity svcLogEntity = svcLogRepository.findByLogDtAndLogSq(log_dt, log_sq);
        svcLogEntity.setResDt(getFullTime());
        svcLogEntity.setResCntnt(ex.toString());
        svcLogEntity.setSttsCd("9000");
        svcLogRepository.save(svcLogEntity);
    }

}
