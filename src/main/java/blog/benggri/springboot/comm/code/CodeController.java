package blog.benggri.springboot.comm.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("comm/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value="getGrpCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCdList(
            @RequestBody Map<String, Object> prmMap
    ) {
        List<Map<String, Object>> result = codeService.getGrpCdList(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getGrpCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getGrpCd(
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeService.getGrpCd(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getCdList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCdList(
            @RequestBody Map<String, Object> prmMap
    ) {
        List<Map<String, Object>> result = codeService.getCdList(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getCd", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getCd(
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = codeService.getCd(prmMap);
        return ResponseEntity.ok(result);
    }

}
