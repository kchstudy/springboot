package blog.benggri.springboot.auth;

import blog.benggri.springboot.auth.vo.LoginReqVo;
import blog.benggri.springboot.auth.vo.SignupReqVo;
import blog.benggri.springboot.auth.vo.TokenReqVo;
import blog.benggri.springboot.comm.constraint.ResEntity;
import blog.benggri.springboot.comm.constraint.ResEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value="signup", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity signup(
            HttpServletRequest request,
            @RequestBody SignupReqVo req
    ) {
        Map<String, Object> result = authService.signup(req);
        return new ResEntity<>(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="login", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity login(
            HttpServletRequest request,
            @RequestBody LoginReqVo req
    ) {
        Map<String, Object> result = authService.login(req);
        return new ResEntity<>(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="reissue", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity reissue(
            HttpServletRequest request,
            @RequestBody TokenReqVo voTokenReq
    ) {
        Map<String, Object> result = authService.reissue(voTokenReq);
        return new ResEntity<>(result, ResEnum.SUCCESS);
    }

}
