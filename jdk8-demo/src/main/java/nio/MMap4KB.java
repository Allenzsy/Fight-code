package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Allenzsy
 * @Date 2022/12/14 23:31
 * @Description:
 */
public class MMap4KB {

    public static void main(String[] args) throws Exception{

        int _4kb = 4 * 1024;
        int _GB = 1024 * 1024 * 1024;
        File file = new File("mmap-4kb.data");
        try(FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_4kb);
            for (int i = 0; i < _4kb; i++) {
                byteBuffer.put((byte)1);
            }
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, _GB);
            long millis = System.currentTimeMillis();
            for (int i = 0; i < _GB; i += _4kb) {
                byteBuffer.position(0);
                byteBuffer.limit(_4kb);
                map.put(byteBuffer);
            }
            System.out.println(System.currentTimeMillis() - millis);
        }


    }



}
