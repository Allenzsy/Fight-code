package algorithm.search;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2023/2/12 23:37
 * @Description:
 */
public class Solution436 {

    @Test
    public void test_1() {
        int[][] intervals = {{3,4},{2,3},{1,2}};
        int[] res = findRightInterval(intervals);
        Arrays.toString(res);
    }

    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int [][] clone = new int[n][2];
        for (int i = 0; i < n; i++) {
            clone[i][0] = intervals[i][0];
            clone[i][1] = i;
        }
        // Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        Arrays.sort(clone, (o1, o2) -> o1[0]-o2[0]);
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int l = 0, r = n-1;
            int target = intervals[i][1]; // 目标数组的最后一个值
            while (l < r) {
                int midPos = l + (r-l)/2;
                int mid = clone[midPos][0];
                if (mid < target) {
                    l = midPos+1;
                } else if (mid >= target) {
                    r = midPos;
                }
            }
            ans[i] = clone[r][0] >= target ? clone[r][1] : -1;
        }

        return ans;

    }
}
