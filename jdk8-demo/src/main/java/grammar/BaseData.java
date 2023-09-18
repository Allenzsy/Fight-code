package grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/8/7 21:02
 * @Description:
 */
public class BaseData {

    public static void main(String[] args) {
        Integer i1 = 40;
        Integer i2 = 40;

        System.out.println(i1 == i2);
        System.out.println(i1.equals(Boolean.TRUE));

        int a = 3, b = 7;
        int d = 5;
        int c = d = a+b;
        System.out.println((c));
        System.out.println((d));

        Integer a1 = 10;
        int a2 = 11;
        final List<Integer> list = new ArrayList<>();
        list.add(Math.max(a1.intValue(), a2));

        final int i = 1 << (16 - 1);
        System.out.println(Integer.toBinaryString(i));
        System.out.println("");
        System.out.println(Integer.toBinaryString(16));
        System.out.println(Integer.numberOfLeadingZeros(16));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(16)));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)));
        System.out.println(Integer.toBinaryString((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16));
        System.out.println((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16);
        System.out.println(Integer.toBinaryString(((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16) + 2));
        System.out.println(((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16) + 2);
        System.out.println("");
        System.out.println(Integer.toBinaryString((1 << 16) - 1));
        System.out.println((1 << 16) - 1);
        System.out.println();
        System.out.println(Integer.toBinaryString((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16));
        System.out.println(Integer.toBinaryString(((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16) >>> 16));
        System.out.println(Integer.toBinaryString((1 << (32 - 16)) - 1));
        System.out.println(Integer.toBinaryString((((Integer.numberOfLeadingZeros(16) | 1 << (16 - 1)) << 16) >>> 16) + (1 << (32 - 16)) - 1));
    }

}
