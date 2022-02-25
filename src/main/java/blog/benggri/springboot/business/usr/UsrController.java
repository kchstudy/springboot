package blog.benggri.springboot.business.usr;

import blog.benggri.springboot.business.usr.vo.ResetUsrPwdReqVo;
import blog.benggri.springboot.business.usr.vo.UpdateUsrInfoReqVo;
import blog.benggri.springboot.business.usr.vo.UsrInfoReqVo;
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

import static blog.benggri.springboot.comm.util.ObjectMapperUtil.objToMap;

@RestController
@RequestMapping("usr")
public class UsrController {

    @Autowired
    private UsrService usrService;

    @RequestMapping(value="getUsrInfo", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getUsrInfo(
        HttpServletRequest request,
        @RequestBody UsrInfoReqVo vo
    ) {
        Map<String, Object> result = usrService.getUsrInfo(objToMap(vo));
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="updateUsrInfo", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity updateUsrInfo(
        HttpServletRequest request,
        @RequestBody UpdateUsrInfoReqVo vo
    ) {
        Map<String, Object> result = usrService.updateUsrInfo(objToMap(vo));
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="resetUsrPwd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity resetUsrPwd(
        HttpServletRequest request,
        @RequestBody ResetUsrPwdReqVo vo
    ) {
        Map<String, Object> result = usrService.resetUsrPwd(objToMap(vo));
        return new ResEntity(result, ResEnum.SUCCESS);
    }

}
