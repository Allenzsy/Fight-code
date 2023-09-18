package reflect;

/**
 * @Author Allenzsy
 * @Date 2022/10/27 19:15
 * @Description:
 */

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class ReflectDemo {

    public static void main(String[] args) throws Exception{
        // 通过 Class.forName() 获取 Class 对象
        Class<?> clazz = Class.forName("reflect.ReflectDemo");

        // 通过反射创建实例
        ReflectDemo instance = (ReflectDemo) clazz.newInstance();

        // 获取指定的无参方法
        Method method = clazz.getDeclaredMethod("hello");
        Annotation[] methodDeclaredAnnotations = method.getDeclaredAnnotations();
        for(Annotation ann : methodDeclaredAnnotations) {
            if(ann instanceof MyAnno) {
                System.out.println(((MyAnno) ann).value());
            }
        }
    }

    @MyAnno
    private void hello() {
        System.out.println("私有方法");
    }

}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno{
    String value() default "123";
}