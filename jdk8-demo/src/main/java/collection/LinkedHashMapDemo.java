package collection;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Allenzsy
 * @Date 2023/8/27 16:00
 * @Description:
 */
public class LinkedHashMapDemo {

    Map<Integer, String> linkedMap = new LinkedHashMap<Integer, String>(){{
        put(0, "000");
        put(1, "111");
        put(2, "222");
        put(3, "333");
        put(4, "444");
    }};

    @Test
    public void test_forEeach() {
        for (Map.Entry<Integer, String> entry : linkedMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        final Set<Integer> set = linkedMap.keySet();

    }

}
