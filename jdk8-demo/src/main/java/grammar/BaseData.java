package grammar;

/**
 * @Author Allenzsy
 * @Date 2022/8/7 21:02
 * @Description:
 */
public class BaseData {

    public static void main(String[] args) {
        Integer i1 = 40;
        Integer i2 = 40;

        Integer i3 = 256;
        Integer i4 = 256;

        System.out.println(i1 == i2);
        System.out.println(i3 == i4);

        int a = 3, b = 7;
        int d = 5;
        int c = d = a+b;
        System.out.println((c));
        System.out.println((d));
    }

}
