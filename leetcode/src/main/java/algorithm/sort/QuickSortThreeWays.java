package algorithm.sort;


import Utils.Util;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2021/12/31 0:31
 * @Description:
 */
public class QuickSortThreeWays {

    public static <T extends Comparable<T>> void partitionThreeWays(T[] arr, int l, int r) {
        /* 1.定义 i 指向当前待 partition 的元素，则有
             [l+1, lt]  < p
             [lt+1, i)  = p
             [gt, r]    > p */
        Util.swap(arr, l, (int)(Math.random()*(r-l+1) + l));
        T p = arr[l];

        int lt = l;     // 初始值选取还是按照初始区间为空
        int gt = r+1;   // 如果这里写 r 并且把下面的 gt-1 改成 gt 则得不到正确的 partition 结果，这是因为 while 的结束条件还是 (i<gt) 要改成 (i<=gt)
        int i = lt+1;

        while (i < gt) {
            if (arr[i].compareTo(p) < 0) { // 当前元素 <p 则和 lt 的后一个元素交换，然后都++
                Util.swap(arr, lt+1, i);
                lt++;
                i++;
            }else if (arr[i].compareTo(p) == 0) { // 当前元素 =p 则只需要 i++ 往后
                i++;
            }else if (arr[i].compareTo(p) > 0) { // 当前元素 >p 只需要和 gt 的前一个元素交换
                Util.swap(arr, i, gt-1);
                gt--;
            }
        }
        Util.swap(arr, l, lt);
        // 由于把 p 和 lt 交换了，因此 lt-- 后才能满足之前的定义
    }

    @Test
    public void test_sort() {
        int N = 20;
        System.out.println("Test for random array, size = " + N + " , random range [0, " + N + "]");
        Integer[] arr1 = Util.generateRandomArray(N, 0, N);
        Integer[] arr2 = new Integer[]{3,15,2,7,1,19,6,10,16,8,11,13,13,17,7,11,19,20,10,13};
        Util.printArray(arr2);
        partitionThreeWays(arr2, 0, arr1.length-1);
        Util.printArray(arr2);
    }

}
