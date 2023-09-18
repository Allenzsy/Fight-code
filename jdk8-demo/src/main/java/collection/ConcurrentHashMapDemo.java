package collection;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Allenzsy
 * @Date 2023/9/11 23:16
 * @Description:
 */
public class ConcurrentHashMapDemo {

    @Test
    public void test_compute() {
        final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("aaa", 111);
        map.put("bbb", 222);
        map.put("ccc", 333);
        map.put("ddd", 444);
        map.put("eee", 555);
        System.out.println("init...");
        System.out.println(map.toString());

        // compute 当 key 存在, 新 value 不是 null, 此时 aaa 的 value 会是 111111
        map.compute("aaa", (k, v) -> 111111);
        // compute 当 key 存在, 新 value 是 null, 此时 bbb 会被删除
        map.compute("bbb", (k, v) -> null);
        // compute 当 key 不存在, 新 value 不是 null, 此时会插入 mmmmmm - 123123
        map.compute("mmmmmm", (k, v) -> 123123);
        // compute 当 key 不存在, 新 value 是 null, 此时什么都不会插入
        map.compute("nnnnnn", (k, v) -> null);

        // computeIfAbsent 当 key 不存在, 新 value 不是 null, 此时会插入 iiiiii - 123123
        map.computeIfAbsent("iiiiii", (k) -> 123123);
        // computeIfAbsent 当 key 不存在, 新 value 是 null, 此时什么都不会插入
        map.computeIfAbsent("jjjjjj", (k) -> null);
        // computeIfAbsent 当 key 存在, 不管 value 是不是 null, 都不会有任何操作
        map.computeIfAbsent("ccc", (k) -> null);
        map.computeIfAbsent("ccc", (k) -> 999);

        // computeIfPresent 当 key 存在, 新 value 不是 null, 此时会插入 iiiiii - 123123
        map.computeIfPresent("ddd", (k, v) -> 123123);
        // computeIfPresent 当 key 存在, 新 value 是 null, 此时什么都不会插入
        map.computeIfPresent("eee", (k, v) -> null);
        // computeIfPresent 当 key 不存在, 不管 value 是不是 null, 都不会有任何操作
        map.computeIfPresent("kkkkkk", (k, v) -> null);
        map.computeIfPresent("kkkkkk", (k, v) -> 123123);


        System.out.println("finished...");
        System.out.println(map.toString());
    }

}
