package collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.PriorityQueue;

/**
 * @Author Allenzsy
 * @Date 2022/8/6 15:03
 * @Description:
 */
public class PriorityQueueDemo {

    public static void main(String[] args) {
    }

    @Test
    public void test_PriorityQueue_add_non_comparable() {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.add(new Person("zsy", 27));
        // 再添加由于既没有实现 Comparable 接口也没有传入 Comparator 会抛出异常
        //queue.add(new Person("dd", 27));
    }

    @Test
    public void test_PriorityQueue_add_comparable_comparator() {
        PriorityQueue<Person> queue = new PriorityQueue<>((p1, p2) -> p1.age - p2.age);
        queue.add(new Person("zsy", 27));
        queue.add(new Person("dd", 27));
    }

    @Data
    @AllArgsConstructor
    class Person {

        String name;
        int age;

    }

}
