package blog.benggri.springboot.view;

import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ViewController {

    @Autowired
    private ViewService viewService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(
            HttpServletRequest request
    ) {
        return "html/index";
    }

    @RequestMapping(value="/v/{menu_id}", method= RequestMethod.GET)
    public String view(
            HttpServletRequest request,
            @RequestParam(value="t")       String t,
            @RequestParam(value="usr_id")  String usr_id,
            @PathVariable(value="menu_id") String menu_id
    ) {

        String result = viewService.getViewPath(MapBuilder.createInstance()
                                                          .add( "usr_id"  , usr_id  )
                                                          .add( "menu_id" , menu_id )
                                                          .toMap());
        return "html/"+result;
    }

}
