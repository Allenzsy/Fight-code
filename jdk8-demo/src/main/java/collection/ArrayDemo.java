package collection;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Author Allenzsy
 * @Date 2022/8/6 11:43
 * @Description:
 */
public class ArrayDemo {

    @Test
    public void test_123() {
        BigDecimal decimal = new BigDecimal("50.50").setScale(2);
        System.out.println(decimal.toString());
        int[] n1 = {7,6,5,4,3};
    }

    public static void main(String[] args) {
        Object[] o1 = {};
        Object[] o2 = {};
        System.out.println(o1 == o2);
        System.out.println(o1.equals(o2));
        System.out.println(Math.ceil(0.5d));
        System.out.println(new BigDecimal(0.55));
        BigDecimal df = new BigDecimal((float) 5/4).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(df);
    }

    public int minSpeedOnTime(int[] dist, double hour) {
        int l = 1, r = Arrays.stream(dist).max().getAsInt();
        int n = dist.length, ans = -1;
        BigDecimal hourD = new BigDecimal(hour).setScale(2, BigDecimal.ROUND_HALF_UP);

        while (l < r) {
            int mid = l + (r-l)/2;
            int temp1 = 0;
            for (int i = 0; i < n-1; i++) {
                temp1 += (dist[i] + mid - 1)/mid;
            }
            BigDecimal temp = new BigDecimal((float)dist[n-1]/mid).setScale(2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(temp1));
            if(temp.compareTo(hourD) > 0) {
                l = mid + 1;
            } else {
                ans = mid;
                r = mid - 1;
            }
        }

        int temp1 = 0;
        for (int i = 0; i < n-1; i++) {
            temp1 += (dist[i] + l - 1)/l;
        }
        BigDecimal temp = new BigDecimal((float)dist[n-1]/l).setScale(2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(temp1));

        return temp.compareTo(hourD) > 0 ? -1 : l;

    }

}
