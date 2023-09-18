package algorithm.dp;

import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2023/7/26 0:44
 * @Description:
 */
public class Solution97 {

    String s1, s2, s3;
    char[][] memo;

     public boolean isInterleave(String s1, String s2, String s3) {
         this.s1 = s1; this.s2 = s2; this.s3 = s3;
         int l1 = s1.length(), l2 = s2.length();
         if (l1+l2 != s3.length()) return false;
         memo = new char[l1+1][l2+1];
         return dfs(l1, l2) == '1';
     }


     char dfs(int i, int j) {
         if (i <= 0 && j <= 0) {
             return '1';
         }

         if (memo[i][j] != '\u0000') {
             return memo[i][j];
         }
         int k = i + j - 1;
         if (i > 0 && s3.charAt(k) == s1.charAt(i-1)) {
             if (dfs(i-1, j) == '1') {
                 return memo[i][j] = '1';
             }
         }
         if (j > 0 && s3.charAt(k) == s2.charAt(j-1)) {
             if (dfs(i, j-1) == '1') {
                 return memo[i][j] = '1';
             }
         }
         return memo[i][j] = '0';
     }

    public boolean isInterleave2(String s1, String s2, String s3) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 + len2 != s3.length()) {
            return false;
        }

        boolean[][] dp = new boolean[len1+1][len2+1];
        dp[0][0] = true; // 初始条件
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                int k = i + j - 1;
                if (i > 0 && s3.charAt(k) == s1.charAt(i-1)) {
                    dp[i][j] = dp[i-1][j];
                }
                if (j > 0 && s3.charAt(k) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        for (boolean[] row : dp) {
            System.out.println(Arrays.toString(row));
        }
        return dp[len1][len2];
    }

    public static void main(String[] args) {
        final String s1 = "aabc";
        final String s2 = "abad";
        final String s3 = "aabadabc";
        boolean interleave = new Solution97().isInterleave(s1, s2, s3);
        interleave = new Solution97().isInterleave2(s1, s2, s3);
        System.out.println(interleave);
        final int[][] ints = {{1, 2}, {2, 3}};
    }
}
