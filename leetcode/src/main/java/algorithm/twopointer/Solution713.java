package algorithm.twopointer;

public class Solution713 {

    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 6};
        new Solution713().numSubarrayProductLessThanK(nums, 100);
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int n = nums.length, ret = 0;
        int prod = 1, i = 0;
        for (int j = 0; j < n; j++) {
            prod *= nums[j];
            while (i <= j && prod >= k) {
                prod /= nums[i];
                i++;
            }
            ret += j - i + 1;
        }
        return ret;
    }

    public synchronized static void maa() {

    }

}
