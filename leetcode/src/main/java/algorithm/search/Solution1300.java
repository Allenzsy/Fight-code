package algorithm.search;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2023/4/11 21:39
 * @Description: 1300. 转变数组后最接近目标值的数组和
 */
public class Solution1300 {

    int n;
    int[] sum;

    public int findBestValue(int[] arr, int target) {
        Arrays.sort(arr);
        n = arr.length;
        sum = new int[n+1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i-1]+arr[i-1];
        }

        int l = 0, r = arr[n-1];
        int ans = 0;
        int pos = 0;
        while (l < r) {
            int mid = l + (r-l+1)/2;
            pos = getPos(arr, mid, target);
            if ((n-pos)*mid + sum[pos] <= target) {
                l = mid;
                ans = mid;
            } else {
                r = mid-1;
            }
        }
        int posTemp = getPos(arr, r, target);
        if ((n-posTemp)*r + sum[posTemp] <= target) {
            ans = r;
            pos = posTemp;
        }
        // 现在只找到 <= target 最接近的值 ans, 因为时绝对值最接近, 此时需要再查看一下 ans+1 是不是更接近
        posTemp = getPos(arr, ans+1, target);
        return Math.abs((n-pos)*ans + sum[pos] - target) <= Math.abs((n-posTemp)*(ans+1) + sum[posTemp] - target) ? ans : ans+1;
    }

    private int getPos(int[] arr, int num, int target) {
        int l = 0, r = n-1;
        // 找大于等于 num 的第一个值
        while (l < r) {
            int mid = l + (r-l)/2;
            if (arr[mid] >= num) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        // 找不到就返回n, 这样相当于计算原有数组所有元素和
        return arr[r] >= num ? r : n;
    }

    @Test
    public void test_main() {
        final Solution1300 solution = new Solution1300();
        int[] nums = new int[]{1,1,2};
        int target = 10;
        final int bestValue = solution.findBestValue(nums, target);
        System.out.println(bestValue);
    }

}
