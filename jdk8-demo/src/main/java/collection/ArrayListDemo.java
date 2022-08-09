package collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author Allenzsy
 * @Date 2022/6/8 23:25
 * @Description:
 */
@Slf4j
public class ArrayListDemo {

    /**
     * 一个有趣的现象,只有两个元素的时候不会抛出快速失败的异常,
     * 这是因为刚好 remove 之后 size 变为1, 此时 hasNext() 就跳出循环了,不会执行 it.next() 因此没有抛出快速失败的异常
     */
    @Test
    public void test_fast_fail() {
        List<String> a = new ArrayList<>();
        a.add("1"); a.add("2"); a.add("3");
        Iterator<String> it = a.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            System.out.println("temp: " + temp);
            if("1".equals(temp)){
                a.remove(temp);
                // it.remove(); // 应使用迭代器的 remove 方法
            }
        }
    }

    /**
     * foreach 最终会编译成迭代器的方式,也具有快速失败机制,
     */
    @Test
    public void test_foreach_fast_fail() {
        List<String> a = new ArrayList<>();
        a.add("1"); a.add("2"); a.add("3");
        for (String temp : a) {
            System.out.println("temp: " + temp);
            if("1".equals(temp)){
                a.remove(temp);
            }
        }
    }

    @Test
    public void test_replace_element() {
        List<Integer> list = IntStream.of(1, 2, 3, 4, 5).boxed().collect(Collectors.toList());
        list.set(2, 100);
        log.info("{}", list);
    }

}
