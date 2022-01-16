package algorithm.sort;

import Utils.Util;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2021/12/29 22:47
 * @Description:
 */
public class QuickSortTwoWays {

    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(arr, 0, arr.length-1);
    }

    public static <T extends Comparable<T>> void sort(T[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int[] pIndex = partitionTwoWays(arr, l, r);
        sort(arr, l, pIndex[0]);
        sort(arr, pIndex[1], r);

    }

    public static <T extends Comparable<T>> int[] partitionTwoWays(T[] arr, int l, int r) {
        /* 1.定义 p 指向第一个元素，作为 partition 的标准
           2.用 i 指向 <=p 数组的下一个值, j 指向 >=p 数组的前一个值，则有
           3.[l+1, i) <= p
             (j, r]   >= p */
        Util.swap(arr, l, (int)(Math.random()*(r-l+1) + l));
        T p = arr[l];
        int i = l+1;
        int j = r;

        while (true) { // 有更简洁的写法但是这样更清晰
            while (i<=r && arr[i].compareTo(p) < 0) {
                i++; // 如果 i 指向的元素一直小于 p 且不越界则直接++
            }
            while (j>=l+1 && arr[j].compareTo(p) > 0) {
                j--; // 如果 j 指向的元素一直大于 p 且不越界则直接--
            }
            if (i > j) {
                break;
            }
            Util.swap(arr, i, j);
            i++;
            j--;
        }
        // 最后的终止位置是 j 在 i 的左边，所以此时 j 指向的位置是 p 元素的最终位置
        Util.swap(arr, l, j);
        return new int[]{j, i};
    }

    @Test
    public void test_sort() {
        int N = 20;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        Util.printArray(arr1);
        sort(arr1, 0, arr1.length-1);
        Util.printArray(arr1);
    }


}
