package algorithm.sort;

import Utils.SortTestHelper;
import Utils.Util;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2021/12/28 21:55
 * @Description:
 */
public class QuickSort {

    public static <T extends Comparable<T>> void sort(T[] arr) {
        sort(arr, 0, arr.length-1);
    }

    public static <T extends Comparable<T>> void sort(T[] arr, int l, int r) {
        if (l>=r) {
            return;
        }
        int pIndex = partition(arr, l, r);
        sort(arr, l, pIndex-1);
        sort(arr, pIndex+1, r);
    }

    private static <T extends Comparable<T>> int partition(T[] arr, int l, int r) {
        /* 1.定义 p 指向第一个元素，作为 partition 的标准
           2.j 指向 <p 部分的数组最后一个元素
           3.i 指向待进行 partition 的元素
           [l+1, j] 是 <p 的元素
           [j+1, i] 是 >p 的元素
         */
        int j = l;   // 初始化成 l 则数组[l+1, j]初始是空的没有元素，注意这种思想反复有用到
        int i = l+1; // 同上，使[j+1, i]初始时空的
        T p = arr[l];// 这里也可以不缓存一直用 arr[l] 表示 partition 的标准

        for (; i <= r; i++) {
            if (arr[i].compareTo(p) < 0) {
                j++; // j 先自增再把小于 P 的元素交换过来，这样j就依旧指向<p 部分的数组最后一个元素了
                Util.swap(arr, j, i);
            }
        }
        Util.swap(arr, l, j);
        return j;
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
