package grammar;

import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2022/8/6 15:30
 * @Description:
 */
public class InterfaceTest {

    @Test
    public void test_which_method() {
        new SubAAImpl().test();
    }

    interface AA {
        default void test() {
            System.out.println("接口默认实现");
        }
    }

    class AAImpl implements AA {
        @Override
        public void test() {
            System.out.println("父类重写接口方法");
        }
    }

    class SubAAImpl extends AAImpl implements AA {

    }

}
