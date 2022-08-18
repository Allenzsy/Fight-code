package algorithm.search;

import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2022/8/19 1:07
 * @Description:
 */
public class BinarySearch {

    public int findFirstPos(int[] num, int target) {
        // 边界
        if(num == null || num.length == 0) { return -1; }
        // 二分查找
        int pos = binarySearch(num, 0, num.length-1, target);
        // 没找到返回-1
        if(pos == -1) { return pos; }
        // 找到第一个
        while(--pos >= 0 && num[pos] == target ) {}
        return ++pos;
    }



    public int binarySearch(int[] num, int l, int r, int target) {
        if(l > r) {
            return -1;
        }
        //int mid = (l + r) >> 1; 存在溢出风险
        int mid = l + ((r - l) >> 1);
        if(target == num[mid]) {
            return mid;
        } else if(target < num[mid]) {
            return binarySearch(num, l, mid-1, target);
        } else {
            return binarySearch(num, mid+1, r, target);
        }
    }



    @Test
    public void test_binarySearch_recursion() {
        int[] num = {1,2,3,4,4,4,4,6,7,8,9,10};
        int pos = binarySearch(num, 0, num.length-1, 6);
        System.out.println(String.format("结果: %s", pos));
        pos = binarySearch(num, 0, num.length-1, 3);
        System.out.println(String.format("结果: %s", pos));
        pos = binarySearch(num, 0, num.length-1, 8);
        System.out.println(String.format("结果: %s", pos));
        pos = findFirstPos(num, 4);
        System.out.println(String.format("结果: %s", pos));
        pos = findFirstPos(num, 9);
        System.out.println(String.format("结果: %s", pos));

    }

}
