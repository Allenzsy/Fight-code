package jvm;

/**
 * @Author Allenzsy
 * @Date 2022/11/28 21:40
 * @Description:
 */
public class LoadClassB  {

    static {
        System.out.println("静态代码块 LoadClassB 是 LoadClassC 的父类, 被初始化了");
    }

    {
        System.out.println("代码块 LoadClassB 是 LoadClassC 的父类, 被初始化了");
    }

}
