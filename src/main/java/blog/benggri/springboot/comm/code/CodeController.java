package blog.benggri.springboot.comm.code;

import blog.benggri.springboot.comm.exception.CustomException;
import blog.benggri.springboot.comm.util.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("comm/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value="getGrpCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCdList(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        List<Map<String, Object>> result = codeService.getGrpCdList(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCd(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeService.getGrpCd(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCdList(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        List<Map<String, Object>> result = codeService.getCdList(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCd(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeService.getCd(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getSucc", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getSucc(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = MapBuilder.createInstance()
                                               .add( "key1" , "value1" )
                                               .add( "key2" , "value2" )
                                               .add( "key3" , "value3" )
                                               .add( "key4" , "value4" )
                                               .toMap();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getError", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getError(
            HttpServletRequest request,
            @RequestBody Map<String, Object> prmMap
    ) {
        throw new CustomException("ERROR");
    }

}
