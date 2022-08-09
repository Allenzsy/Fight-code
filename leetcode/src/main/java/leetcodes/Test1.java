package leetcodes;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2022/5/9 1:54
 * @Description:
 */
public class Test1 {

    class Ans {
        public String str;
        public int len;
        Ans(String str, int len) {
           this.str = str;
           this.len = len;
        }
    }

    public String decodeString(String s) {
        return dfs(s, 0).str;
    }

    Ans dfs(String s, int num) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbNum = new StringBuilder();
        int i = 0;
        for(i = 0; i < s.length() && !"]".equals(s.substring(i, i+1)); i++) {
            if('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                sbNum.append(s.substring(i, i+1));
                continue;
            } else if("[".equals(s.substring(i, i+1))){
                int n = Integer.parseInt(sbNum.toString());
                Ans ans = dfs(s.substring(i+1), n);
                sb.append(ans.str);
                i = i + ans.len;
                sbNum = new StringBuilder();
                continue;
            }
            sb.append(s.substring(i, i+1));
        }
        String temp = sb.toString();
        for(int j = 0; j < num-1; j++) {
            sb.append(temp);
        }
        return new Ans(sb.toString(), i+1);
    }

    public static void main(String[] args) {
        //System.out.println(new Test().decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
        //System.out.println(new Test().splitArraySameAverage(new int[]{1,2,3,4,5,6,7,8}));
        new Test1().dfsRebuild(new Integer[]{2,null,3,null,4}, 0, 4);
    }




    boolean[] used;

    public boolean splitArraySameAverage(int[] nums) {
        int n = nums.length;
        used = new boolean[n];
        BigDecimal[] bNums = new BigDecimal[n];
        int sum = 0;
        for(int i = 0; i < n; i++) {
            sum += nums[i];
        }
        BigDecimal target = new BigDecimal(sum << 2);
        BigDecimal avg = new BigDecimal(sum).divide(new BigDecimal(n));
        Arrays.sort(nums);
        for(int i = 0; i < n; i++) {
            bNums[i] = new BigDecimal(nums[i]).add(avg);
        }
        return backtracking(bNums, 0, n, target);

    }

    private boolean backtracking(BigDecimal[] bNums, int begin, int n, BigDecimal target) {
        if(BigDecimal.ZERO.compareTo(target) == 0) {
            return true;
        }

        for(int i = begin; i < n; i++) {
            // 重复情况
            if(i > 0 && bNums[i].compareTo(bNums[i-1]) == 0 && !used[i-1]) {
                continue;
            }
            if(BigDecimal.ZERO.compareTo(target.subtract(bNums[i])) > 0) {
                return false;
            }
            used[i] = true;
            boolean res = backtracking(bNums, i+1, n, target.subtract(bNums[i]));
            used[i] = false;
            if(res) {
                return true;
            }

        }
        return false;
    }

    private TreeNode dfsRebuild(Integer[] vals, int begin, int end) {

        if(vals[begin] == null) {
            return null;
        }
        Integer nodeVal = vals[begin];
        TreeNode node = new TreeNode(nodeVal);
        if(begin == end) {
            return node;
        }

        int mid = -1;
        for(int i = begin+1; i <= end; i++) {
            if(vals[i] == null) continue;
            if(vals[i] >= nodeVal) {
                mid = i;
                break;
            }
        }
        if(mid == -1) {
            node.left = dfsRebuild(vals, begin+1, end-1);
            node.right = null;
        } else {
            node.left = dfsRebuild(vals, begin+1, mid-1);
            node.right = dfsRebuild(vals, mid, end);
        }

        return node;
    }

    class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
     }
}
