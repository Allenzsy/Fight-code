package jvm;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;

/**
 * @Author Allenzsy
 * @Date 2022/11/28 23:31
 * @Description:
 */
@Slf4j
public class MyClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        log.info("类的全限定名: {}", name);
        String path = Paths.get(System.getProperty("user.dir"), name+".class").toString();
        File file = new File(path);
        byte[] bytes = getClassBytes(file);
        // defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
        Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
        return c;
    }

    private byte[] getClassBytes(File file) {
        // 这里要读入.class的字节，因此要使用字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fc = fis.getChannel();
             WritableByteChannel wbc = Channels.newChannel(baos);
             ) {
            ByteBuffer by = ByteBuffer.allocate(1024);
            while (true){
                int i = fc.read(by);
                if (i == 0 || i == -1)
                    break;
                by.flip();
                wbc.write(by);
                by.clear();
            }
        } catch (IOException e) {
            log.info("读取异常 {}", e);
        }
        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = Class.forName("jvm.MyClass", true, myClassLoader);
        Object obj = clazz.newInstance();
        Class<?> clazz2 = Class.forName("MyClass2", true, myClassLoader);
        Object obj2 = clazz2.newInstance();

        log.info("MyClass 类被加载: {}", obj);
        // 打印出应用类加载器AppClassLoader
        log.info("由于在 idea 编译路径下(相当于在 classpath 下): {}", obj.getClass().getClassLoader());

        log.info("Myclass2 类被加载: {}", obj2);
        // 打印出我们的自定义类加载器
        log.info("自定义类加载加载是: {}", obj2.getClass().getClassLoader());
    }
}
