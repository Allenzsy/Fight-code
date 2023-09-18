import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test1 {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        System.out.println(System.getProperty("user.dir"));
        System.out.println(test1.getClass().getClassLoader().getResource("Test.class"));

        double ans = 1.0009;
        int ii = 1;
        System.out.println(ans/4 + ii);
        System.out.println(Math.random());
        HashMap<String, String> hashMap = new HashMap<>();
        String s1 = hashMap.getOrDefault("1", "123");
        System.out.println(s1);
        hashMap.putIfAbsent("2", "222");
        hashMap.putIfAbsent("2", "123");
        System.out.println(hashMap);

        final int i = (int) Math.sqrt(15);

        int[] num = new int[]{1,2,3,4,5,5,5,5,5,5,5};
        Arrays.stream(num).max();
        Map<Integer, Integer> map = IntStream.of(num).boxed().collect(Collectors.toMap(k -> k, v -> 1, (v1, v2) -> v1 + v2));
        System.out.println(map.getOrDefault(1, 2));
        System.out.println(map.getClass());
        IntStream.of(num).sum();
        for (Map.Entry<Integer, Integer> entry: map.entrySet()){
            System.out.println("key :" + entry.getKey() + ", value: " + entry.getValue());
        }
        System.out.println((new int[11]).length);
        //int[] nums = {1,0,1,0,1};
        //System.out.println(numSubarraysWithSum(nums, 2));
        String allowed = "AGHJJKK".substring(0,2);
        AtomicStampedReference reference = new AtomicStampedReference(allowed, 2);
        boolean b = reference.compareAndSet(allowed, "234234", 2, 3);
        if(b) System.out.println(reference.getReference());

        int[] aa1 = new int[]{1,2,3,4,45,5};


        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(() -> System.out.println("执行任务"));

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));


        Future<Object> future = pool.submit(() -> "返回值");
        try {
            Object s = future.get();
        } catch (InterruptedException e) {
            // 处理中断异常
        } catch (ExecutionException e) {
            // 处理无法执行任务异常
        } finally {
            // 关闭线程池 executor.shutdown();
        }
        pool.shutdown();

        Set<Character> collect = allowed.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        //Arrays.stream({1,2,3}).
        char[] chars = allowed.toCharArray();
        Character[] aa = {'1', '2', '2'};
        //Arrays.stream(aa)
        Set<Character> hash = new HashSet<Character>();
        int[] a = {123, 123};
        StringBuilder builder = new StringBuilder();
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        System.out.println("123".substring(3,3));
        new HashMap<Character, String>(){{
            put('1', "asdasd");
        }};
        Arrays.stream(new int[]{0}).boxed().collect(Collectors.toList());
    }

    public static int numSubarraysWithSum(int[] nums, int goal) {
        HashMap<Integer, Integer> sums = new HashMap<>();
        int sum = 0;
        for(int e : nums) {
            sums.put(sum, sums.getOrDefault(sum, 0) + 1);
            sum += e;
        }
        sums.put(sum, sums.getOrDefault(sum, 0) + 1);
        int ans = 0;
        for(Map.Entry<Integer, Integer> entry : sums.entrySet()) {
            ans += entry.getValue() * sums.getOrDefault(entry.getKey() + goal, 0);
        }

        return ans;

    }

    @Test
    public void test_aa() {
        SimpleDateFormat targetFmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowtim=targetFmt.format(new Date());
        Long nowT=Long.parseLong(nowtim)+6000000/(60 * 60 * 24 * 1000);
        String intervalAfterPreGetTime = nowT+"";
        System.out.println(intervalAfterPreGetTime);
    }

/*            <dependency>
                <groupId>org.apache.pdfbox</groupId>
                <artifactId>pdfbox</artifactId>
                <version>2.0.6</version>
            </dependency>
            <dependency>
                <groupId>org.apache.pdfbox</groupId>
                <artifactId>pdfbox-tools</artifactId>
                <version>2.0.6</version>
            </dependency>
            <dependency>
                <groupId>org.icepdf.os</groupId>
                <artifactId>icepdf-core</artifactId>
                <version>6.2.2</version>
                <exclusions>
                    <exclusion>
                        <groupId>javax.media</groupId>
                        <artifactId>jai_core</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>*/

    /**
     * 将指定pdf文件的首页转换为指定路径的缩略图
     *
     * @param filepath 原文件路径，例如d:/test.pdf
     * @param imagepath 图片生成路径，例如 d:/
     * @param zoom 缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     * @return 生成图片名称列表
     */
    /*public void tranfer(String filepath, String imagepath, float zoom) throws PDFException,
            PDFSecurityException, IOException, InterruptedException{
        // ICEpdf document class
        Document document = null;
        float rotation = 0f;
        document = new Document();
        document.setFile(filepath);
        // maxPages = document.getPageTree().getNumberOfPages();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage img =
                    (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                            org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, zoom);
            Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
            ImageWriter writer = (ImageWriter) iter.next();
            File outFile =
                    new File(imagepath + (filepath.replace(imagepath, "").split("\\.")[0]) + "-" + (i + 1)
                            + ".jpg");
            FileOutputStream out = new FileOutputStream(outFile);
            ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
            writer.setOutput(outImage);
            writer.write(new IIOImage(img, null, null));
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            new Test().tranfer("E:/java/课程笔记/coding-71/03-Sorting-Advance/Chapter-3-watermarked.pdf", "E:/java/课程笔记/coding-71/03-Sorting-Advance", 1f);
        } catch (PDFException | PDFSecurityException | IOException e) {
            System.err.println("出错啦: " + e.getMessage());
        }
    }*/



}
