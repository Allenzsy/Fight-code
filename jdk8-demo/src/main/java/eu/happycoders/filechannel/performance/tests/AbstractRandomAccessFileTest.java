package eu.happycoders.filechannel.performance.tests;

import eu.happycoders.filechannel.performance.TestParams;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public abstract class AbstractRandomAccessFileTest extends AbstractChannelTest {

    public AbstractRandomAccessFileTest(TestParams testParams, String displayName, boolean directByteBuffer) {
        super(testParams, displayName, directByteBuffer);
    }

    @Override
    void runTestInternal(Path path) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw");
             FileChannel channel = file.getChannel()) {
            writeToChannel(buffer, channel);
        }
    }

}
