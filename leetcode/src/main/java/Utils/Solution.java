package Utils;

/**
 * @Author Allenzsy
 * @Date 2023/4/27 18:31
 * @Description:
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 例如：
 输入：n = 3
 输出：[
 "((()))",
 "(()())",
 "(())()",
 "()(())",
 "()()()"
 ]

 */
public class Solution {

    public static final char l = '(';
    public static final char r = ')';

    public int lCount = 1;
    public int rCount = 0;

    public StringBuilder tempStr = new StringBuilder("(");
    public List<String> ans = new ArrayList<>();

    public List<String> gen(int n) {
        if (n <= 0) {
            return ans;
        }
        backtrace(1, n);
        return ans;
    }

    private void backtrace(int index, int n) {
        // 终止
        if (index == 2*n) {
            ans.add(tempStr.toString());
            return;
        }
        if (lCount < n) {
            lCount++;
            tempStr.append(l);
            backtrace(index+1, n);
            tempStr.deleteCharAt(index);
            lCount--;
        }
        if (rCount < lCount) {
            rCount++;
            tempStr.append(r);
            backtrace(index+1, n);
            tempStr.deleteCharAt(index);
            rCount--;
        }

    }

    @Test
    public void test_case1() {
        final Solution solution = new Solution();
        final List<String> list = solution.gen(3);
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void test_case2() {
        final Solution solution = new Solution();
        final List<String> list = solution.gen(-1);
        for (String s : list) {
            System.out.println(s);
        }
    }

}
