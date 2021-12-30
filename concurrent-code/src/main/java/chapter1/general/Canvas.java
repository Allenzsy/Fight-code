package chapter1.general;

import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2021/12/30 23:18
 * @Description:
 */
public class Canvas {

    public void drawAll(List<? extends Shape> shapes) {
        for (Shape s : shapes) {
            s.draw(this);
        }
    }

}
