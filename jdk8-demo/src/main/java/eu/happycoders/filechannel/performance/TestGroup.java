package eu.happycoders.filechannel.performance;

import eu.happycoders.filechannel.performance.tests.*;

import java.io.IOException;
import java.util.ArrayList;

public class TestGroup {

    private final TestParams testParams;
    private final int repetitions;

    TestGroup(TestParams testParams, int repetitions) {
        this.testParams = testParams;
        this.repetitions = repetitions;
    }

    void run() throws IOException, ReflectiveOperationException {
        System.out.printf("Results for %d tests à %d MB with bufferSize = %d KB, writeMode = %s, flush = %b:%n",
                repetitions, testParams.fileSize() >> 20, testParams.bufferSize() >> 10, testParams.writeMode(), testParams.isFlush());

        ArrayList<AbstractTest> tests = new ArrayList<AbstractTest>() {{
            add(new MappedByteBufferTest(testParams));
            add(new FileChannelDirectTest(testParams));
            add(new FileChannelHeapTest(testParams));
            add(new RandomAccessFileDirectTest(testParams));
            add(new RandomAccessFileHeapTest(testParams));
            add(new FileChannelViaOutputStreamDirectTest(testParams));
            add(new FileChannelViaOutputStreamHeapTest(testParams));
            add(new FileOutputStreamTest(testParams));
        }};

        for (int i = 0; i < repetitions; i++) {
            for (AbstractTest test : tests) {
                test.runTest(i);
            }
        }

        tests.forEach(AbstractTest::printStats);
        System.out.println();
    }

}
