package grammar;

/**
 * @Author Allenzsy
 * @Date 2023/2/10 0:32
 * @Description:
 */
public class ConstantPool {

    public static void main(String[] args) {
        String str = String.valueOf(6666666);
        str.intern();
        String str2 = "6666666";
        System.out.print(str == str2);
    }

}
