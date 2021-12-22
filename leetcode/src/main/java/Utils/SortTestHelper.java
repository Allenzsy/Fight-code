package Utils;

/**
 * @Author Allenzsy
 * @Date 2021/12/23 0:21
 * @Description:
 */
public class SortTestHelper {

    public static <T> void testSort(T[] arr, SortExecutor<T> sortExecutor) {
        sortExecutor.execute(arr);
    }

}
