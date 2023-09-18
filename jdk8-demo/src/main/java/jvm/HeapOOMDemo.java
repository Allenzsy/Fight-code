package jvm;

import java.util.ArrayList;

/**
 * @Author Allenzsy
 * @Date 2022/8/28 11:27
 * @Description: VM Args： -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOMDemo {

    static class OOMObject {
    }

    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
