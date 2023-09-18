package algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Allenzsy
 * @Date 2022/11/25 18:55
 * @Description:
 */
@Slf4j
public class KMP {

    int kmp(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        int s = 0, p = 0; // s 为遍历 str 的指针, p 为遍历 pattern 的指针
        int[] match = buildMatch(str);

        // 防止越界
        while (s < n && p < m) {
            // 相等就两个指针都往后走
            if (str.charAt(s) == pattern.charAt(p)) { s++; p++; }
            // match[p-1] 刚好是头部匹配的位置, +1 就是下一个位置了
            else if (p > 0) { p = match[p-1] + 1; }
            // 说明刚开始就不匹配, 此时只需要 s 后移
            else { s++; }
        }
        // 此时如果 p 已经走到末尾说明匹配上了, 否则没匹配上
        return p == m ? s - m : -1;
    }

    int[] buildMatch(String str) {
        int m = str.length();
        int i, j;
        // match 的含义, match[j] = max{i} where p0...pi == pj-i...pj 且 j>i
        int[] match = new int[m];
        match[0] = -1;

        for(j = 1; j < m; j++) { // 进行动态规划递推
            i = match[j-1];
            // 不相等的情况, 需要一致往前找
            while(i>=0 && match[i+1] != str.charAt(j))
                i = match[i];
            if(match[i+1] == str.charAt(j)) { // 如果是找到相等的, 则赋值
                match[j] = match[i] + 1;
            } else { // 说明一直找到最前面还没找到
                match[j] = -1;
            }
        }
        return match;
    }

    public static void main(String[] args) {
        String s = "kjasdfgjkadbfkbjasdhgo";
        String pattern = "bja";
        KMP kmp = new KMP();
        int index = kmp.kmp(s, pattern);
        log.info(s.substring(index, index+pattern.length()));
    }
}
