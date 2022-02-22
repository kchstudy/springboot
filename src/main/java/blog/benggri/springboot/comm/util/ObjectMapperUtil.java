package blog.benggri.springboot.comm.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ObjectMapperUtil {

    public static List listToList(List prm) {
        ObjectMapper om = new ObjectMapper();
        List result = om.convertValue(prm, List.class);
        return result;
    }

    public static Map mapToMap(Map prm) {
        ObjectMapper om = new ObjectMapper();
        Map result = om.convertValue(prm, Map.class);
        return result;
    }

    public static List strToList(String prm) {
        ObjectMapper om = new ObjectMapper();
        try {
            List result = om.readValue(prm, List.class);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map strToMap(String prm) {
        ObjectMapper om = new ObjectMapper();
        try {
            Map result = om.readValue(prm, Map.class);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static List objToList(Object prm) {
        ObjectMapper om = new ObjectMapper();
        List result = om.convertValue(prm, List.class);
        return result;
    }

    public static Map objToMap(Object prm) {
        ObjectMapper om = new ObjectMapper();
        Map result = om.convertValue(prm, Map.class);
        return result;
    }
}
