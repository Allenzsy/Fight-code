import Utils.SortExecutor;
import Utils.SortTestHelper;
import Utils.Util;
import algorithm.sort.SelectionSort;

public class Main {
    public static void main(String[] args) {

        Integer[] a = {10,9,8,7,6,5,4,3,2,1};
        Integer[] b = {10,9,8,7,6,5,4,23,12,3,2,1};
        SortTestHelper.testSort(a, SelectionSort::sort);
        Util.printArray(a);

        SortExecutor<Integer> executor = SelectionSort::sort;
        executor.execute(b);
        Util.printArray(b);

    }
}
