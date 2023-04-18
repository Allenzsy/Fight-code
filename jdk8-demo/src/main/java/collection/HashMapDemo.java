package collection;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    void test_iteration() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            map.put(i, i);
        }
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key: "+entry.getKey()+", value: "+ entry.getValue());
        }

    }

}
