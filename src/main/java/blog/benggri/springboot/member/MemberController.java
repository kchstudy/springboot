package blog.benggri.springboot.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value="getDataList", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getDataList(
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = memberService.getDataList(prmMap);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="getData", method= RequestMethod.POST, produces="application/json; charset=utf8")
    public ResponseEntity getData(
            @RequestBody Map<String, Object> prmMap
    ) {
        Map<String, Object> result = memberService.getData(prmMap);
        return ResponseEntity.ok(result);
    }

}
