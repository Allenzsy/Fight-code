import Utils.SortExecutor;
import Utils.SortTestHelper;
import Utils.Util;
import algorithm.sort.InsertionSort;
import algorithm.sort.MergeSort;
import algorithm.sort.SelectionSort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int N = 20000;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");

        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        Integer[] arr2 = Arrays.copyOf(arr1, arr1.length);
        Integer[] arr3 = Arrays.copyOf(arr1, arr1.length);
        SortTestHelper.testSort(arr1, SelectionSort::sort, "选择排序");
        SortTestHelper.testSort(arr2, InsertionSort::sort, "插入排序");
        SortTestHelper.testSort(arr3, MergeSort::sort, "归并排序");

        /* 另外一种不使用其他类的方法再调用，直接使用接口定义变量并直接指向符合参数类型的方法引用
        SortExecutor<Integer> executor = SelectionSort::sort;
        executor.execute(b);
        Util.printArray(b);*/

    }
}
