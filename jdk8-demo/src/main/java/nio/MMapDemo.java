package nio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author Allenzsy
 * @Date 2022/12/11 15:40
 * @Description:
 */
public class MMapDemo {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        final URL resource = MMapDemo.class.getClassLoader().getResource("demo.txt");
        final Path path = Paths.get(resource.toURI());
        final FileChannel open = FileChannel.open(path, StandardOpenOption.READ);
        // 发起系统调用 mmap
        final MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0, open.size());
        // 读取数据时，不会再出发调用 read,直接从自己的虚拟内存中即可拿数据
        final CharBuffer decode = StandardCharsets.UTF_8.decode(map);
        System.out.println(decode.toString());
        open.close();
        Thread.sleep(100000);
    }

}


