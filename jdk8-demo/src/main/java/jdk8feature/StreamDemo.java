package jdk8feature;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    /**
     * 求并集, 交集, 补集
     */
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
        final long count = A.stream().filter(e -> B.contains(e)).count();
        log.info("交集: {}", AnB);
        // A中B的补集: A-B
        List<String> A_B = A.stream().filter(e -> !B.contains(e)).collect(Collectors.toList());
        log.info("A中B的补集: {}", A_B);
    }

    @Test
    public void test_min_max_sum_sort() {
        int[] num = {3,6,1,6,7};
        OptionalInt min1 = Arrays.stream(num).min();
        min1.ifPresent((s) -> System.out.println("不是空就打印:"+s));
        String[] strs  = {"123", "aaa", "333"};
        Arrays.stream(strs).max((s1, s2) -> s1.compareTo(s2)).get();

        // 求最小值
        int min = Arrays.stream(num).min().getAsInt();
        // 求最大值
        int max = Arrays.stream(num).max().getAsInt();
        // 求和
        int sum = Arrays.stream(num).sum();
        // 排序
        int[] sorted = Arrays.stream(num).sorted().toArray();
        // 求平均值
        double average = Arrays.stream(num).average().getAsDouble();

        log.info("原始数组: {}", num);
        log.info("求最小值: {}", min);
        log.info("求最大值: {}", max);
        log.info("求和: {}", sum);
        log.info("排序: {}", sorted);
        log.info("求平均值: {}", average);

    }

    @Test
    public void test_flatmap() {
        String[] strings = {"aa", "bg", "ddd", "zsy"};
        String[] array = Stream.of(strings).flatMap(l -> Stream.of(l)).toArray(String[]::new);
        System.out.println(123);
        //List<int[]> list1 = Stream.of(new int[]{1, 2, 3}).collect(Collectors.toList());
        List<Integer> list1 = Arrays.stream(new int[]{7, 8, 9}).boxed().collect(Collectors.toList());
        List<Integer> list2 = Arrays.stream(new int[]{1, 2, 3}).boxed().collect(Collectors.toList());
        List<List<Integer>> list3 = Stream.of(list1, list1).collect(Collectors.toList());

        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);list.add(list2);

        List<List<Integer>> list4 = list.stream().flatMap(l -> Stream.of(l)).collect(Collectors.toList());
        List<Integer> list5 = list.stream().flatMap(l -> l.stream().map(e -> e-1)).collect(Collectors.toList());
        list.stream().flatMap(l -> Stream.of(l)).forEach(System.out::println);
        list.stream().flatMap(l -> l.stream()).forEach(System.out::println);
        System.out.println(333);
    }

    @Test
    public void test_Arrays() {
        int[][] nums = new int[][]{{3,4}, {1,2}, {5,6}, {7,8}};
        int[][] nums1 = new int[0][0];
        Arrays.stream(nums).forEach(e -> System.out.println(Arrays.toString(e)));

        final int[] objects = Arrays.stream(nums).mapToInt(e -> e[0]).sorted().toArray();

        System.out.println(Arrays.toString(Arrays.stream(nums).map(e -> e[0]).sorted().toArray()));
        System.out.println(Arrays.toString(Arrays.stream(nums1).map(e -> e[0]).sorted().toArray()));
        System.out.println(Arrays.toString(Arrays.stream(nums1).map(e -> e[1]).sorted().toArray()));
    }

    @Test
    public void test_aa() {
        int[] commands = new int[]{-2,8,3,7,-1};
        int[][] obstacles = new int[][] {
                {-4,-1},
                {1,-1},
                {1,4},
                {5,0},
                {4,5},
                {-2,-1},
                {2,-5},
                {5,1},
                {-3,-1},
                {5,-3}
        };

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int px = 0, py = 0, d = 0;
        Set<Integer> set = new HashSet<Integer>();
        for (int[] obstacle : obstacles) {
            set.add(obstacle[0] * 60001 + obstacle[1]);
        }
        int res = 0;
        for (int c : commands) {
            if (c < 0) {
                d += c == -1 ? 1 : -1;
                d %= 4;
                d = d < 0 ? d+4 : d;
            } else {
                for (int i = 0; i < c; i++) {
                    if (set.contains((px + dirs[d][0]) * 60001 + py + dirs[d][1])) {
                        break;
                    }
                    px += dirs[d][0];
                    py += dirs[d][1];
                    res = Math.max(res, px * px + py * py);
                }
            }
        }
        System.out.println(res);
    }

    @Test
    public void test_strToInt() {
        String[] str = new String[]{"123", "12", "4356", "7890"};
        Arrays.stream(str).mapToInt(Integer::valueOf).toArray();


    }

}
