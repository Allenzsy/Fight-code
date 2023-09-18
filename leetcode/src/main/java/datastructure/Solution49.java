package datastructure;

import java.util.*;

/**
 * @Author Allenzsy
 * @Date 2022/11/7 22:33
 * @Description:
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
 *
 */
public class Solution49 {

    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            char[] temp = str.toCharArray();
            Arrays.sort(temp);
            String sTemp = new String(temp);
            List<String> list = map.getOrDefault(sTemp, new LinkedList<String>());
            list.add(str);
        }

        List<List<String>> ans = new LinkedList<>();
        for(Map.Entry<String, List<String>> kv : map.entrySet()) {
            ans.add(kv.getValue());
        }

        return ans;
    }

    public static void main(String[] args) {
        new Solution49().groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
    }

}
