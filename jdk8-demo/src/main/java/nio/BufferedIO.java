package nio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author Allenzsy
 * @Date 2023/1/7 19:57
 * @Description:
 */
public class BufferedIO {

    public static void main(String[] args) throws Exception{

        int _4kb = 4 * 1024;
        long _GB = 4 * 1024L * 1024L * 1024L;
        File file = new File("bufferedIO-4kb.data");
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file, true))) {
            byte[] bytes = new byte[_4kb];
            for (int i = 0; i < _4kb; i++) {
                bytes[i] = (byte) 2;
            }
            long millis = System.currentTimeMillis();
            for (long i = 0; i < _GB; i += _4kb) {
                stream.write(bytes);
            }
            System.out.println(System.currentTimeMillis() - millis);
        }
    }

}
