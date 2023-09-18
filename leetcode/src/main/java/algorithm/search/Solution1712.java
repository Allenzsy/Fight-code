package algorithm.search;

import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2023/2/15 0:42
 * @Description:
 */
public class Solution1712 {

    @Test
    public void test_1() {
        int[] nums = {1,1,1};
        System.out.println(new Solution1712().waysToSplit(nums));
    }

    /**
     前缀和 + 二分法
     固定第一段, 二分求第二段的右指针的左边界和右边界, 相减得到此时的分割方案数量
     */
    public int waysToSplit(int[] nums) {
        int n = nums.length;
        // 直接在原数组上求前缀和
        for (int i = 1; i < n; i++) {
            nums[i] += nums[i-1];
        }
        int ans = 0;
        // 保证有三段, 所以最大到 n-3
        for (int i = 0; i < n-2; i++) {
            int sum1 = nums[i];
            int l = i+1, r = n-2;
            // 寻找满足条件的第二段右指针的左边界
            while (l < r) {
                int midPos = l + (r-l)/2;
                int sum2 = nums[midPos]-sum1;
                int sum3 = nums[n-1] - nums[midPos];
                if (sum2 >= sum1) {
                    if (sum2 <= sum3) {
                        r = midPos;
                    } else {
                        r = midPos-1;
                    }
                } else {
                    l = midPos + 1;
                }
            }
            int start = nums[l]-sum1 >= sum1 && nums[l]-sum1 <= nums[n-1]-nums[l] ? l : -1;
            if (start == -1) continue;

            // 寻找满足条件的第二段右指针的右边界
            l = i+1; r = n-2;
            while (l < r) {
                int midPos = l + (r-l+1)/2;
                int sum2 = nums[midPos]-sum1;
                int sum3 = nums[n-1] - nums[midPos];
                if (sum2 >= sum1) {
                    if (sum2 <= sum3) {
                        l = midPos;
                    } else {
                        r = midPos-1;
                    }
                } else {
                    l = midPos + 1;
                }
            }
            int end = nums[l]-sum1 >= sum1 && nums[l]-sum1 <= nums[n-1]-nums[l] ? l : -1;
            if (end == -1) continue;
            ans += end-start+1;
        }
        return ans;
    }
}
