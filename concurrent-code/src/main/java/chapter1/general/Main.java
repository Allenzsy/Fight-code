package chapter1.general;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2021/12/30 23:24
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        List<? extends Shape> shapes= new ArrayList<Rectangle>();
//        shapes.add(new Rectangle()); 检查和编译都报错
//        shapes.add(new Origin()); 检查和编译都报错
        List<Rectangle> rectangles= new ArrayList<Rectangle>();
        rectangles.add(new Rectangle());
        rectangles.add(new Rectangle());
        shapes = rectangles;
        for (Shape s: shapes) {
            s.draw(new Canvas());
        }

        List<? super Shape> sps = new ArrayList<Origin>();

//        sps.add(new Origin());



    }

}
