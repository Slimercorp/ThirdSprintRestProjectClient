import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestConsumer {
    final static String sensorName = "TempSensor2000";

    public static void main(String[] args) {
        //registerSensor();
        //fillData();
    }

    private static void registerSensor() {

        //Register sensor
        final String urlRegister = "http://localhost:8080/sensors/registration";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(urlRegister, jsonData);
    }

    private static void fillData() {
        // Send 1000 random data
        final String urlAdd = "http://localhost:8080/measurements/add";
        for (int i=0; i<1000; i++) {
            double temp = Math.random() * 60.0 - 30.0;
            boolean raining = Math.random() > 0.5;

            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("value", temp);
            jsonData.put("raining", raining);
            jsonData.put("sensor", Map.of("name", sensorName));

            makePostRequestWithJSONData(urlAdd, jsonData);
        }
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Изменение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}
