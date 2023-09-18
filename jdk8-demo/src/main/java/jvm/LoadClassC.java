package jvm;

/**
 * @Author Allenzsy
 * @Date 2022/11/28 21:46
 * @Description:
 */
public class LoadClassC extends LoadClassB {

    static {
        System.out.println("静态代码块 LoadClassC 被初始化了");
    }

    {
        System.out.println("代码块 LoadClassC 被初始化了");
    }


    public static final String STATIC_FINAL_STR= "访问 static final 的变量不会触发类初始化";
    public static String STATIC_STR= "访问 static 的变量会触发类初始化";

}
