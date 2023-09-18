package algorithm.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Allenzsy
 * @Date 2022/6/29 15:36
 * @Description:
 */
public class Solution {

    public static void main(String[] args) {
        String s = "0110111";
        int len = s.length();
        int start = 0, end = 0, i = 0;
        int MOD = (int)1e9+7;
        long ans = 0;
        while(i < len) {
            if(s.charAt(i) == '1') {
                start = i;
                while(i < len && s.charAt(i) == '1'){
                    i++;
                }
                end = i - 1;
                if(start == i) ans = ans % MOD + 1;
                ans = ((end-start+2) * (end-start+1) / 2) % MOD + ans;
                continue;
            }
            i++;
        }
        System.out.println(ans);
    }

    @Test
    public void test() {
        System.out.println(String.valueOf(34799));
        String s = String.valueOf(99743);
        char[] c = s.toCharArray();
        List<Integer> ori = new ArrayList<>();
        int tempNum = 1993;
        for (int i = 0; tempNum > 0; i++) {
            Integer e = tempNum % 10;
            tempNum /= 10;
            ori.add(e);
        }
        Collections.reverse(ori);
        List<Integer> sorted = ori.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        System.out.println(ori);
        System.out.println(sorted);
        int iOri = 0;
        int iSorted = 0, k = 0;
        while (iSorted < sorted.size()) {
            if(ori.get(iOri) != sorted.get(iSorted)) {
                break;
            }
            iOri++;
            iSorted++;
        }
        int target = sorted.get((iSorted));
        int needBeSwapedindex = iOri;
        iOri = ori.size() - 1;
        while (iOri > needBeSwapedindex) {
            iOri--;
            if(target == ori.get(iOri)) {
                ori.set(iOri, ori.get(needBeSwapedindex));
                ori.set(needBeSwapedindex, target);
                break;
            }
        }
        int ans = 0;
        for (int i = 0; i < ori.size(); i++) {
            ans = ans*10 + ori.get(i);
        }
        System.out.println(ans);
    }

    @Test
    void test1(){
        new Integer(1).intValue();
    }

}
