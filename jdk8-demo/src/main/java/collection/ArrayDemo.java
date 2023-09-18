package collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author Allenzsy
 * @Date 2022/8/6 11:43
 * @Description:
 */
@Slf4j
public class ArrayDemo {

    @Test
    public void test_oneDimensionArray(){
        Integer[] o1 = {};
        Integer[] o2 = {};
        log.info("o1 与 o2 是否 ==: {}", o1 == o2);
        log.info("o1 与 o2 是否 equals: {}", o1.equals(o2));

        int[] nums1 = {3,1,5,2,4};
        int[] nums2 = {3,1,5,2,4};
        log.info("nums1 与 nums2 是否 equals: {}", nums1.equals(nums2));
        Arrays.sort(nums1);
        log.info("排序后结果: {}", Arrays.toString(nums1));

        int a = 1;

    }

    @Test
    public void test_multiDimensionArray() {
        int[][] nums = {{9,0},
                        {3,4},
                        {2,3}};
        // 与 Arrays.sort(nums, (n1, n2) -> n1[0] - n2[0]); 相同
        Arrays.sort(nums, Comparator.comparingInt(n -> n[0]));
        System.out.println(Arrays.deepToString(nums));

        int [][] nums1 = new int[8][2];
        System.out.println(Arrays.deepToString(nums1));

        int [][] nums2 = new int[8][];
        System.out.println(Arrays.deepToString(nums2));
    }

}
