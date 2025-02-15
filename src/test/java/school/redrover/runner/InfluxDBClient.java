package school.redrover.runner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InfluxDBClient {
    private static final String INFLUXDB_URL = "http://a2657cf0c126.vps.myjino.ru:49169/api/v2/write?org=my-org&bucket=allure_bucket&precision=ns";
    private static final String INFLUXDB_TOKEN = "0Klh5P629Vi81gMABcaEYLWb4ijaah9QHzJAijMWUa0Lej9yIp2DEhJeBTmRKm0CgBDTgopO9IdwMzRIuP8qHA==";

    public static void sendMetric(String testName, String status) {
        String data = "test_status,TestName=" + testName + " status=\"" + status + "\"";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(INFLUXDB_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Token " + INFLUXDB_TOKEN);
            connection.setDoOutput(true);
            connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
