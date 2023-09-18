package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * @Author Allenzsy
 * @Date 2022/12/14 18:01
 * @Description:
 */
public class FileChannel1B {

    public static void main(String[] args) throws Exception {

        long millis = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(20);
        System.out.println(System.currentTimeMillis() - millis);

        int _4kb = 4 * 1024;
        int _GB = 1024 * 1024 * 1024;
        File file = new File("db-1b.data");
        try(FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel()){
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);
            byteBuffer.put((byte)3);
            millis = System.currentTimeMillis();
            for (int i = 0; i < _GB; i ++) {
                byteBuffer.position(0);
                byteBuffer.limit(1);
                fileChannel.write(byteBuffer);
            }
            System.out.println(System.currentTimeMillis() - millis);
        }

    }

}
