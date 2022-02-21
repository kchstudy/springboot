package blog.benggri.springboot.comm.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public MapBuilder add(String key , Object value){
        super.put(key,value);
        return this;
    }

    public static MapBuilder createInstance(){
        return new MapBuilder();
    }

    public static MapBuilder createInstance(Map<String, Object> map){
        MapBuilder builder = new MapBuilder();
        builder.putAll(map);
        return builder;
    }

    public Map<String, Object> toMap(){
        return this;
    }

}
