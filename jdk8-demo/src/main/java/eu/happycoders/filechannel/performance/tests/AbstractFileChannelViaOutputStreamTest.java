package eu.happycoders.filechannel.performance.tests;

import eu.happycoders.filechannel.performance.TestParams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public abstract class AbstractFileChannelViaOutputStreamTest extends AbstractChannelTest {

    public AbstractFileChannelViaOutputStreamTest(TestParams testParams, String displayName, boolean directByteBuffer) {
        super(testParams, displayName, directByteBuffer);
    }

    @Override
    void runTestInternal(Path path) throws IOException {
        try (FileOutputStream out = new FileOutputStream(path.toFile());
            FileChannel channel = out.getChannel()) {
            writeToChannel(buffer, channel);
        }
    }

}
