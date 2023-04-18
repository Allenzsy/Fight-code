package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class AA implements Runnable{

    public static final BigDecimal THRESHOLD = new BigDecimal("25.00");

    public static void main(String[] args) throws Exception{

        ArrayList<Thread> threads = new ArrayList<>();
        Thread thread = null;
        for (int i = 0; i < 100; i++) {
            thread = new Thread(new AA());
            threads.add(thread);
        }
        for (int i = 0; i < 100; i++) {
            threads.get(i).start();
        }
        thread.join();
    }

    @Override
    public void run() {
        String data = "{\n" +
                "  \"id\": 12312323,\n" +
                "  \"name\": \"张宾\",\n" +
                "  \"type\": \"2\",\n" +
                "  \"innode\": false,\n" +
                "  \"properties\":" +
                "{\n" +
                "    \"name\": \"张宾\",\n" +
                "    \"invtype\": \"\",\n" +
                "    \"SYRtype\": \"直接或间接控股,关键管理人员\",\n" +
                "    \"cgzb\": \"34.559\",\n" +
                "    \"invtype_desc\": \"自然人\",\n" +
                "    \"palgorithmid\": \"asdgioadfjopadopfjpqerpjpj34534tpowkedpogkqwerg\",\n" +
                "    \"isSYR\": \"true\"\n" +
                "  }\n" +
                "}";
        long start = System.currentTimeMillis();
        try {
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            ArrayList<List<Object>> lists = new ArrayList<>(200);
            for (int i = 0; i < 500; i++) {
                String type = jsonObject.get("type").getAsString();
                if ("2".equals(type)) {
                    JsonObject properties = jsonObject.get("properties").getAsJsonObject();
                    String syRtype = properties.get("SYRtype").getAsString();
                    if (syRtype != null && syRtype.contains("控股")) {
                        BigDecimal cgzb = properties.get("cgzb").getAsBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP);
                        if (THRESHOLD.compareTo(cgzb) <= 0) {
                            String name = properties.get("name").getAsString();
                            lists.add(Stream.of(name, cgzb).collect(Collectors.toList()));
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            String name = Thread.currentThread().getName();
            log.info("{} 线程耗时: {}", name, end-start);
        } catch (Exception e) {
            log.error("发生异常: {}", e);
        }

    }

}
