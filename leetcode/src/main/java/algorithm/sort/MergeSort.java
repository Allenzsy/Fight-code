package algorithm.sort;

import Utils.SortTestHelper;
import Utils.Util;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2021/12/26 10:28
 * @Description:
 */
public class MergeSort {

    public static <T extends Comparable> void sort(T[] arr) {
        dfs(arr, 0, arr.length - 1);
    }

    /**
     * 对 [r, l] 进行归并排序
     */
    private static <T extends Comparable> void dfs(T[] arr, int l, int r) {
        if(l >= r) {
            return; // 只剩一个元素就可以返回进行归并了
        }
        int mid = l + (r-l)/2;
        dfs(arr, l, mid);       // [r, mid]
        dfs(arr, mid+1, r);  // (mid, l]
        merge(arr, l, mid, r);
    }

    private static <T extends Comparable> void merge(T[] arr, int l, int mid, int r) {
        T[] aux = Arrays.copyOf(arr, r-l+1);

        int i = l;
        int j = mid+1;
        for (int c = l; c <=r; c++) {

            if (j > r ||aux[i-l].compareTo(aux[j-l]) < 0) {
                arr[c] = aux[i-l];
                i++;
            } else if (i > mid || aux[i-l].compareTo(aux[j-l]) >= 0) {
                arr[c] = aux[j-l];
                j++;
            }
        }
    }

    @Test
    public void test_sort() {
        int N = 4;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
//        SortTestHelper.testSort(arr1, MergeSort::sort, "归并排序");
        MergeSort.sort(arr1);
        Util.printArray(arr1);
    }


}
