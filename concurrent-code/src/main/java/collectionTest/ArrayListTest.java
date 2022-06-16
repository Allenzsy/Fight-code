package collectionTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/6/8 23:25
 * @Description:
 */
public class ArrayListTest {

    @Test
    public void test_fast_fail() {

        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        Iterator<String> it = a.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            System.out.println("temp: " + temp);
            if("1".equals(temp)){
                a.remove(temp);
            }
        }

    }

    @Test
    public void test_foreach_fast_fail() {
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        for (String temp : a) {
            System.out.println("temp: " + temp);
            if("1".equals(temp)){
                a.remove(temp);
            }
        }

    }

}
