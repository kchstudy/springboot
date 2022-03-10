package blog.benggri.springboot.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ViewController {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(
            HttpServletRequest request
    ) {
        return "html/index";
    }

    @RequestMapping(value="/v/{menu_id}", method= RequestMethod.GET)
    public String view(
            HttpServletRequest request,
            @RequestParam(value="t") String t,
            @PathVariable(value="menu_id") String menu_id
    ) {

        return "html/index";
    }

}
