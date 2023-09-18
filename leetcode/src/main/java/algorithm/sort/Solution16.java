package algorithm.sort;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2023/7/10 1:06
 * @Description:
 */
class Solution16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int n = nums.length;
        int ans = Integer.MIN_VALUE + 10000;
        int i = 0, j = 0, k = 0;
        for (; i < n-2; i++) {
            // 去重
            if (i > 0 && nums[i] == nums[i-1]) { continue; }
            j = i+1;
            k = n-1;
            while (j < k && j < n-1) {
                if (j > i+1 && nums[j] == nums[j+1]) {
                    j++;
                    continue;
                }
                int tmpSum = nums[i] + nums[j] + nums[k];
                ans = Math.abs(ans - target) <= Math.abs(tmpSum - target) ? ans : tmpSum;
                if (tmpSum > target) {
                    k--;
                } else if (tmpSum < target) {
                    j++;
                } else {
                    return tmpSum;
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        new Solution16().threeSumClosest(new int[]{0, 1, 2}, 0);
    }
}
