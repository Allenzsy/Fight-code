package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Allenzsy
 * @Date 2023/4/4 0:09
 * @Description:
 */
public class Solution981 {

    public static void main(String[] args) {
        final TimeMap timeMap = new TimeMap();
        timeMap.set("love","high",10);
        timeMap.set("love","low",20);
        System.out.println(timeMap.get("love", 5));
    }
}


class TimeMap {

    Map<String, List<VT>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        List<VT> list = map.getOrDefault(key, new ArrayList<VT>());
        list.add(new VT(value, timestamp));
        if (list.size() == 1) {
            map.put(key, list);
        }
    }

    public String get(String key, int timestamp) {
        List<VT> list = map.get(key);
        if (list == null) {
            return "";
        } else {
            return binarySearch(list, timestamp);
        }
    }

    private String binarySearch(List<VT> list, int target) {
        int l = 0, r = list.size()-1;
        String ans = "";
        while (l < r) {
            int mid = l + (r-l+1)/2;
            VT vt = list.get(mid);
            if (vt.timestamp <= target) {
                l = mid;
                ans = vt.value;
            } else {
                r = mid-1;
            }
        }
        VT vt = list.get(r);
        return vt.timestamp <= target ? vt.value : ans;
    }
}

class VT {
    public String value;
    public int timestamp;

    VT(String _value, int _timestamp) {
        value = _value;
        timestamp = _timestamp;
    }
}
