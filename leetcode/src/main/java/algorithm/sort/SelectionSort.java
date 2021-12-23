package algorithm.sort;

import Utils.SortTestHelper;
import Utils.Util;
import org.junit.Test;

public class SelectionSort {

    public static <T extends Comparable> void sort(T[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = i;
            for (int j = i+1; j < len; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            Util.swap(arr, i, minIndex);
        }
    }

    @Test
    public void test_sort() {
        int N = 20;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        SortTestHelper.testSort(arr1, SelectionSort::sort, "选择排序");
        Util.printArray(arr1);
    }

}
