package algorithm.sort;

import Utils.Util;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2021/12/29 22:47
 * @Description:
 */
public class QuickSortTwoWays {

    public static <T extends Comparable> void sort(T[] arr) {

    }

    public static <T extends Comparable> void sort(T[] arr, int l, int r) {

    }

    public static <T extends Comparable> int[] partitionTwoWays(T[] arr, int l, int r) {
        /* 1.定义 p 指向第一个元素，作为 partition 的标准
           2.用 i 指向 <=p 数组的下一个值, j 指向 >=p 数组的前一个值
           3.[l+1, i) 是 <=p 的元素
             (j, r] 是 >=p 的元素 */
        int pIndex = (int) (Math.random()*(r-l+1) + l);
        Util.swap(arr, l, pIndex);
        T p = arr[l];
        System.out.println(p);
        int i = l+1;
        int j = r;

        while (true) {
            while (i<=r && arr[i].compareTo(p) < 0) {
                i++;
            }
            while (j>=l+1 && arr[j].compareTo(p) > 0) {
                j--;
            }
            if (i > j) {
                break;
            }
            Util.swap(arr, i, j);
            i++;
            j++;
        }
        Util.swap(arr, l, j);
        return new int[]{i, j};
    }

    @Test
    public void test_sort() {
        int N = 20;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        Util.printArray(arr1);
        partitionTwoWays(arr1, 0, arr1.length-1);
        Util.printArray(arr1);
    }


}
