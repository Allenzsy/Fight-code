package Utils;

/**
 * @Author Allenzsy
 * @Date 2021/12/23 0:21
 * @Description:
 */
public class SortTestHelper {

    /**
     * 通过函数式接口实现通过排序类名::方法名运行排序函数
     * @param arr
     * @param sortExecutor
     * @param <T>
     */
    public static <T extends Comparable<T>> void testSort(T[] arr, SortExecutor<T> sortExecutor, String name) {
        long startTime = System.currentTimeMillis();
        sortExecutor.execute(arr);
        long endTime = System.currentTimeMillis();

        assert Util.isSorted(arr);
        System.out.println(String.format("%s耗时：%s ms", name, endTime-startTime));
    }

}
