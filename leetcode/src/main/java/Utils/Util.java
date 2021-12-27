package Utils;

import java.util.Random;

/**
 * @Author Allenzsy
 * @Date 2021/12/22 23:26
 * @Description:
 */
public class Util {

    private static final Random RANDOM = new Random();

    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <T> void printArray(T[] arr) {
        for( int i = 0 ; i < arr.length ; i ++ ){
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    // 生成有n个元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
    public static Integer[] generateRandomArray(int n, int rangeL, int rangeR) {
        assert rangeL <= rangeR;

        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            // 生成左闭，右闭
            arr[i] = new Integer((int) (Math.random() * (rangeR - rangeL + 1) + rangeL));
            // 第二种写法
//            arr[i] = new Integer(RANDOM.nextInt(rangeR)%(rangeR-rangeL+1) + rangeL);
        }
        return arr;
    }

    // 判断arr数组是否有序
    public static <T extends Comparable> boolean isSorted(T[] arr){

        for( int i = 0 ; i < arr.length - 1 ; i ++ )
            if( arr[i].compareTo(arr[i+1]) > 0 )
                return false;
        return true;
    }


}
