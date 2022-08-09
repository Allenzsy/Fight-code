package datastructure;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Allenzsy
 * @Date 2022/8/10 1:03
 * @Description:
 */
public class LRUCache extends LinkedHashMap<Integer, Integer> {

    int limit;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.limit = capacity;
    }

    public int get(int key) {
        return super.get(key);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    /**
     * 判断节点数是否超限
     * @param eldest
     * @return 超限返回 true，否则返回 false
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return super.size() > limit;
    }

}
