package blog.benggri.springboot.business.code.mng;

import blog.benggri.springboot.comm.constraint.ResEntity;
import blog.benggri.springboot.comm.constraint.ResEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("code/mng")
public class CodeMngController {

    @Autowired
    private CodeMngService codeMngService;

    @RequestMapping(value="getGrpCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCdList(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.getGrpCdList(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="getGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.getGrpCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="createGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity createGrpCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.createGrpCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="updateGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity updateGrpCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.updateGrpCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="deleteGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity deleteGrpCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.deleteGrpCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="getCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCdList(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.getCdList(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="getCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.getCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="createCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity createCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.createCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="updateCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity updateCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.updateCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }

    @RequestMapping(value="deleteCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity deleteCd(
        HttpServletRequest request,
        @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeMngService.deleteCd(prmMap);
        return new ResEntity(result, ResEnum.SUCCESS);
    }
}
