package blog.benggri.springboot.comm.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K,V> extends HashMap<K,V> {

    private static final long serialVersionUID = 1L;

    public MapBuilder<K,V> add(K key , V value){
        super.put(key,value);
        return this;
    }

    public static <K,V> MapBuilder<K,V> createInstance(){
        return new MapBuilder<K,V>();
    }

    public static <K,V> MapBuilder<K,V> createInstance(Map<K,V> map){
        MapBuilder<K,V> builder = new MapBuilder<>();
        builder.putAll(map);
        return builder;
    }

    public Map<K,V> toMap(){
        return this;
    }

}
