package datastructure;

/**
 * @Author Allenzsy
 * @Date 2023/5/29 0:25
 * @Description: 6. N 字形变换
 * https://leetcode.cn/problems/zigzag-conversion/?envType=study-plan-v2&envId=top-interview-150
 */
public class Solution6 {

    public static void main(String[] args) {
        new Solution6().convert("PAYPALISHIRING", 3);
    }

    public String convert(String s, int numRows) {
        int cnt = numRows*2-2;
        StringBuilder[] strs = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            strs[i] = new StringBuilder();
        }

        for (int i = 0; i < s.length(); i+=cnt) {
            int j = 0;
            for (; j<numRows && j+i<s.length(); j++) {
                strs[j].append(s.charAt(i+j));
            }
            for (j = numRows-2; j>0 && j+numRows-1+i<s.length(); j--) {
                strs[j].append(s.charAt(j+numRows-1+i));
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            ans.append(strs[i]);
        }

        return ans.toString();
    }

}
