package blog.benggri.springboot.comm.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String getPrintStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

}
