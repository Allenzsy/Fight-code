package algorithm.sort;

import Utils.Util;

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

}
