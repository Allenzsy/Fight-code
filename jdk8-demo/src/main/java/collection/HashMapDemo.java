package collection;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Allenzsy
 * @Date 2022/10/31 22:12
 * @Description:
 */
@Slf4j
public class HashMapDemo {

    public static void main(String[] args) {
        HashMap<String, List> map = new HashMap<>(64);
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < 10; i++) {
            List<Integer> list = Arrays.stream(a).boxed().collect(Collectors.toList());
            map.put(""+i, list);
        }


        //Set<String> keySet = map.keySet();
        for (int i = 0; i < 5; i++) {
            for (String key :
                    map.keySet()) {
                List list = map.get(key);
                list.remove(0);
                log.info(key);
            }
            log.info("########{}{}", i, "#########");
        }


    }


}
