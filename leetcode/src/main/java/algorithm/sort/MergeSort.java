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

    /**
     * 对外提供统一的接口
     */
    public static <T extends Comparable> void sort(T[] arr) {
        dfs(arr, 0, arr.length - 1);
    }

    /**
     * 对 [r, l] 进行归并排序，分治算法思想
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

    /**
     * 对 [l, mid] 和 [mid+1, r] 进行归并，大致思想是用 3 个指针，分别指向
     * 1. 要合并到的数组首元素
     * 2. 左侧待合并数组的首元素
     * 3. 右侧待合并数组的首元素
     */
    private static <T extends Comparable> void merge(T[] arr, int l, int mid, int r) {
        T[] aux = Arrays.copyOf(arr, r-l+1);

        int i = l;      // i指向左侧待归并数组的首元素
        int j = mid+1;  // j指向右侧待归并数组的首元素
        for (int c = l; c <=r; c++) {
            // 1.如果左侧待归并的数组都已经遍历完了，就只需要把右侧数组剩余的元素都放入 arr 中
            if (i > mid) {
                arr[c] = aux[j - l];
                j++;
            // 2.若相反，就只需要把左侧数组剩余的元素都放入 arr 中
            } else if (j > r){
                arr[c] = aux[i-l];
                i++;
            // 没有任意一组元素遍历完就正常判断哪个元素小，就放入 arr 中
            } else if (aux[i-l].compareTo(aux[j-l]) >= 0){
                arr[c] = aux[j - l];
                j++;
            } else if (aux[i-l].compareTo(aux[j-l]) < 0) {
                arr[c] = aux[i-l];
                i++;
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
