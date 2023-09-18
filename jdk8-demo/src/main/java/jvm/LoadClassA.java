package jvm;

/**
 * @Author Allenzsy
 * @Date 2022/11/28 21:40
 * @Description: 类加载测试
 */
public class LoadClassA {

    /**
     * 下面的测试每次只能执行一个语句才能看出效果
     * @param args
     */
    public static void main(String[] args) {
        // 创建类会触发类和其父类的初始化
        new LoadClassC();
        // 访问 static 的变量会触发类和其父类初始化
        //System.out.println(LoadClassC.STATIC_STR);
        // 访问 static final 的变量不会触发类初始化
        //System.out.println(LoadClassC.STATIC_FINAL_STR);
    }

}
