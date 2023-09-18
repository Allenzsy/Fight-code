package eu.happycoders.filechannel.performance.util;

import sun.nio.ch.DirectBuffer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

public class MemoryMappedBufferHelper {

    public static void unsafeUnmap(MappedByteBuffer mappedByteBuffer) throws ReflectiveOperationException {
        if ("1.8".equals(System.getProperty("java.vm.specification.version"))) {
            // for jdk8
            ((DirectBuffer) mappedByteBuffer).cleaner().clean();
        } else {
            // Doing this with reflection:
            // sun.misc.Unsafe.theUnsafe.invokeCleaner(mappedByteBuffer)
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Method invokeCleanerMethod = unsafeClass.getMethod("invokeCleaner", ByteBuffer.class);
            invokeCleanerMethod.setAccessible(true);
            Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            Object theUnsafe = theUnsafeField.get(null);
            invokeCleanerMethod.invoke(theUnsafe, mappedByteBuffer);
        }
    }

}
