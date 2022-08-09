package jdk8feature;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class StreamDemo {

    public static List<String> A = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
    public static List<String> B = new ArrayList<>(Arrays.asList("3", "4", "5", "6", "7"));

    /**
     * 数组转集合，集合转数组
     */
    @Test
    public void test_arr_between_collection() {
        // 集合转数组
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        int[] arr = list.stream().mapToInt(Integer::intValue).toArray();

        // 数组转集合
        int[] arr1 = {1,2,3,4,5,6};
        List<Integer> list1 = Arrays.stream(arr1).boxed().collect(Collectors.toList());
        List<Integer> list2 = IntStream.of(arr1).boxed().collect(Collectors.toList());
    }

    @Test
    public void test_set_operation() {
        // 求并集
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(A);
        temp.addAll(B);
        List<String> AuB = temp.stream().distinct().collect(Collectors.toList());
        log.info("并集: {}", AuB);
        // 求交集
        List<String> AnB = A.stream().filter(e -> B.contains(e)).collect(Collectors.toList());
        log.info("交集: {}", AnB);
        // A中B的补集: A-B
        List<String> A_B = A.stream().filter(e -> !B.contains(e)).collect(Collectors.toList());
        log.info("A中B的补集: {}", A_B);
    }

}
