package grammar;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Allenzsy
 * @Date 2022/8/6 0:22
 * @Description: 静态的早于实例的、父类的早于子类的、构造方法最晚
 */
@Slf4j
public class OutterClass {

    {
        log.info("外部类: 非静态代码块每次实例化执行...");
    }
    static {
        log.info("外部类: 静态代码块类加载时执行...");
    }


    public static void main(String[] args) {
        log.info("-------------单独执行外部类构造函数 start-------------");
        new OutterClass();
        log.info("-------------单独执行外部类构造函数  end -------------");

        OutterClass.testUseInnerClassInstance();
    }

    /**
     * 静态方法中:
     * 1. 可以直接 new 静态内部类实例(new 多次会创建不同对象)
     * 2. 不能直接 new 普通内部类实例, 可以先 new 出外部类实例然后 外部实例.new 内部类构造函数创建
     */
    public static void testUseInnerClassInstance() {

        // 不能直接创建,因为普通内部类实例需要外部类先实例化, 内部类实例持有外部类实例的引用
        //new InnerClass();
        OutterClass outterClass = new OutterClass();
        InnerClass innerClass = outterClass.new InnerClass();

        // new 多次静态内部类实例会创建不同对象
        InnerClassStatic innerClassStatic1 = new InnerClassStatic();
        InnerClassStatic innerClassStatic2 = new InnerClassStatic();
        log.info("两个静态内部类实例是否相同{}, 分别是 {} 和 {}",innerClassStatic1 == innerClassStatic2, innerClassStatic1, innerClassStatic2);

    }


    /**
     * 普通内部类
     */
    public class InnerClass{
        {
            log.info("普通内部类: 非静态代码块每次实例化执行...");
        }

    }

    public static class InnerClassStatic {
        {
            log.info("静态内部类: 非静态代码块每次实例化执行...");
        }
        static {
            log.info("静态内部类: 静态代码块静态内部类加载时执行一次...");
        }
    }

}
