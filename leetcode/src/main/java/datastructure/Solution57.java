package datastructure;

/**
 * @Author Allenzsy
 * @Date 2022/11/8 0:28
 * @Description:
 */
public class Solution57 {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        if(n == 0) {
            int[][] res = new int[1][];
            res[0] = newInterval;
            return res;
        }

        int[][] ans = new int[n+1][2];
        int idx = 0;
        for(int i = idx; i < n; i++) {
            int nextIdx = i+1 < n ? i+1 : i;
            int[] temp = intervals[i];
            int[] next = intervals[i];
            if(temp[0] <= newInterval[0] && newInterval[0] <= temp[1]) {
                ans[idx][0] = temp[0];
                break;
            } else if (temp[1] <= newInterval[0] && newInterval[0] <= next[0]) {
                ans[idx][0] = temp[1];
                break;
            } else {
                ans[idx++] = temp;
            }
        }

        for(int i = idx; i < n; i++) {
            int nextIdx = i+1 < n ? i+1 : i;
            int[] temp = intervals[i];
            int[] next = intervals[i];
            if(temp[0] <= newInterval[1] && newInterval[1] <= temp[1]) {
                ans[idx++][1] = temp[1];
            } else if(temp[1] <= newInterval[1] && newInterval[1] <= next[0]) {
                ans[idx++][1] = next[0];
            } else {
                ans[idx++] = temp;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] insert = {{1,3}, {6,9}};
        int[] newInsert = {2,5};
        new Solution57().insert(insert, newInsert);
    }

}
