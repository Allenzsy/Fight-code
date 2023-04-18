package collection;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

    public static void main(String[] args) throws Exception{

        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1,2);
        map.put(2,2);
        map.put(3,2);
        System.out.println(map.get(0));
        Field tail = map.getClass().getDeclaredField("tail");
        tail.setAccessible(true);
        Map.Entry<Integer,Integer> entry=(Map.Entry<Integer, Integer>) tail.get(map);
        Integer key = entry.getKey();
        Integer value = entry.getValue();
        System.out.println(key);
        System.out.println(value);
    }
}
