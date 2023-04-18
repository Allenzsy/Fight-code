package jvm;

public class ConstantPool {

    public static void main(String[] args) {
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        String s4 = "11";
        String ss = s3.intern();
        System.out.println(s3 == s4);
        System.out.println(s3 == ss);
        System.out.println(s4 == ss);

    }
}
