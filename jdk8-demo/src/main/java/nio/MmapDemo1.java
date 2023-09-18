package nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Allenzsy
 * @Date 2022/12/11 16:11
 * @Description:
 */
public class MmapDemo1 {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        FileOutputStream fos = null;
        FileChannel channel = null;
        RandomAccessFile threadWriteFile = null;
        try {
            // asd
            File logfile = new File("./logfileChannelone.bin");
            threadWriteFile = new RandomAccessFile(logfile, "rwd");
            channel = threadWriteFile.getChannel();
            //FileChannel.open("./logfileChannelone.bin", )
            ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 5);
            List<String> list = Stream.of("1234567890abcd",
                                        "1234567890abce",
                                        "1234567890abcf",
                                        "1234567890abcg",
                                        "1234567890abch"
                                        ).collect(Collectors.toList());
            int index = 0;
            for (String e : list) {
                buffer.put(e.getBytes());
                buffer.put("\r\n".getBytes());
                buffer.flip();
                channel.write(buffer, index);
                index += e.length()+2;
                buffer.clear();
            }
            System.out.println("已完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                    channel = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
