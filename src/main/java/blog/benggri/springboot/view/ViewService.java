package blog.benggri.springboot.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static blog.benggri.springboot.comm.util.StringUtil.isEmptyObj;
import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
@Service
public class ViewService {

    @Autowired
    private ViewDao viewDao;

    public String getViewPath(Map<String, Object> prmMap) {
        Map<String, Object> viewInfo = viewDao.getUsrMenuInfo(prmMap);
        if ( isEmptyObj(viewInfo) ) {
            return "redirect:/";
        }

        viewDao.createUsrMenuUseH(viewInfo);
        return nvl(viewInfo.get("menu_path"));
    }

}
