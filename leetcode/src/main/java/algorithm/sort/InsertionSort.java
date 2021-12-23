package algorithm.sort;

import Utils.SortTestHelper;
import Utils.Util;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2021/12/23 23:27
 * @Description: 插入排序
 */
public class InsertionSort {


    public static <T extends Comparable> void sort(T[] arr) {
        int len = arr.length;
        for (int i = 0; i <len; i++) {
            T temp = arr[i]; // 缓存元素 arr[i] 等找到合适的位置再插入
            int j;
            for (j = i; j > 0 && (temp.compareTo(arr[j-1]) < 0); j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
    }

        /**
         * 可以看到插入排序能够 early stop 提前终止，
         * 但是由于这里还是使用了每次都交换元素，因此
         * 新能还没优化到最佳
         * @param arr
         * @param <T>
         */
    public static <T extends Comparable> void sort_slow(T[] arr) {
        int len = arr.length;
        for (int i = 0; i <len; i++) {
            // 寻找元素arr[i]合适的插入位置
            // 写法1：
            for (int j = i; j > 0 && (arr[j].compareTo(arr[j-1]) < 0); j--) {
                Util.swap(arr, j, j-1);
            }
            /*写法2：
            for (int j = i; j > 0; j--) {
                if (arr[j].compareTo(arr[j-1]) < 0) {
                    Util.swap(arr, j, j-1);
                } else {
                    break;
                }
            }*/
        }
    }

    @Test
    public void test_sort() {
        int N = 20;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        SortTestHelper.testSort(arr1, InsertionSort::sort, "插入排序");
        Util.printArray(arr1);
    }

}
