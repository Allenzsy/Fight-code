package algorithm.dp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Allenzsy
 * @Date 2022/11/2 22:45
 * @Description:
 */
public class caca1 {
    Map<Character, Character[]> map = new HashMap<>();

    {
        char letter = 'a';
        for(char c = '2'; c <= '9'; c++) {
            int num = c == '9' ? 4 : 3;
            Character[] chars = new Character[c];
            int i = 0;
            for(; i < num; i++) {
                chars[i] = (char) (letter+i);
            }
            map.put(c, chars);
            letter = (char) (letter+i);
        }
    }

    public static void main(String[] args) {
        caca1 caca1 = new caca1();
        int i = 100_000_7;
        //System.out.println("123");

    }

    class Solution {
        public int search(int[] nums, int target) {
            int n = nums.length;
            if(nums.length == 1) {
                return nums[0] == target ? 0 : -1;
            }

            int i = 0, j = n-1;
            while(i < j) {
                int mid = j - (j - i) / 2;
                if(nums[mid] > nums[i]) {
                    i = mid;
                } else if(nums[mid] < nums[j]) {
                    j = mid;
                }
            }

            int end = nums[n-1] >= target ? n-1 : i;
            int start = nums[n-1] >= target ? i+1 : 0;
            int mid = 0;
            while(start < end) {
                mid = end - (end - start) / 2;
                if(nums[mid] < target) {
                    start = mid+1;
                } else if (nums[mid] > target) {
                    end = mid-1;
                } else if(nums[mid] == target) {
                    return mid;
                }
            }
            return nums[mid] == target ? mid : -1;

        }
    }

    public double[] convertTemperature(double celsius) {

        double[] ans = new double[2];
        BigDecimal cel = new BigDecimal(celsius);
        BigDecimal kelvin = cel.add(new BigDecimal(273.15));
        BigDecimal fahrenheit = cel.multiply(new BigDecimal(1.80)).add(new BigDecimal(32.00));
        ans[0] = kelvin.doubleValue();
        ans[1] = fahrenheit.doubleValue();
        return ans;

    }

}


