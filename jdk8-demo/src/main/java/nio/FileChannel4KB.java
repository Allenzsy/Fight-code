package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Allenzsy
 * @Date 2022/12/14 17:38
 * @Description:
 */
public class FileChannel4KB {

    public static void main(String[] args) throws Exception {
        int _4kb = 4 * 1024;
        long _GB = 4 * 1024L * 1024L * 1024L;
        File file = new File("db-4kb.data");
        try(FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_4kb);
            for (int i = 0; i < _4kb; i++) {
                byteBuffer.put((byte)2);
            }
            long millis = System.currentTimeMillis();
            for (long i = 0; i < _GB; i += _4kb) {
                byteBuffer.position(0);
                byteBuffer.limit(_4kb);
                fileChannel.write(byteBuffer);
            }
            System.out.println(System.currentTimeMillis() - millis);
        }

    }

}
