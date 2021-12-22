package Utils;

/**
 * @Author Allenzsy
 * @Date 2021/12/23 0:18
 * @Description:
 */
@FunctionalInterface
public interface SortExecutor<T> {
    // 执行任意排序类的 sort 方法
    void execute(T[] arr);
}
