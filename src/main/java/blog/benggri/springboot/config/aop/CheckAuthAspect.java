package blog.benggri.springboot.config.aop;

import blog.benggri.springboot.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckAuthAspect {

    private final TokenProvider tokenProvider;

    @Around("execution(* blog.benggri.springboot.view.ViewController.view(..))")
    public Object checkAuth(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        HttpServletRequest request = (HttpServletRequest) args[0];
        String token  = (String) args[1];
        String usrId  = (String) args[2];

        if (!tokenProvider.validateToken(token)) {
            return "redirect:/";
        }

        Authentication authentication = tokenProvider.getAuthentication(token);
        String tokenUsrId = authentication.getName();
        if ( !tokenUsrId.equals(usrId) ) {
            return "redirect:/";
        }

        Object result = pjp.proceed(); // 실제 컨트롤러 로직이 수행되는 구간
        return result;
    }

}
