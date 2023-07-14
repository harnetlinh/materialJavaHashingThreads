package com.example.lab3demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    private static final String GET_URL = "http://103.118.28.46:3000/get-quote";

    private static final String POST_URL = "http://103.118.28.46:3000/add-quote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.textQuote);
    }
    // sử dụng phương thức get để lấy dữ liệu
    private void sendGetHttpUrlConnection() throws Exception{
        URL url = new URL(GET_URL);
        // mở giao thức Http
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // phương thức sử dụng là GET
        conn.setRequestMethod("GET");
        // config request
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            // xử lý dữ liệu trả về
            InputStream responseBody = conn.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

            // Xử lý Json trong java
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject();
            String quote = getValue("quote", jsonReader);
            resultText.setText(quote);

        } else {
            // TODO: Xử lý kết quả trả về là lỗi
        }
    }

    //send POST request
    private void sendPostHttpURLConnection() throws Exception{
        URL url = new URL(POST_URL);
        // mở giao thức Http
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // phương thức sử dụng là GET
        conn.setRequestMethod("POST");
        // config request
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoOutput(true);

        // set data body
        JSONObject data = new JSONObject();
        // set key value
        data.put("name", "Ha Ngoc Linh");

        // đưa data object json vào request post
        byte[] postData = data.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream outputStream = conn.getOutputStream()){
            outputStream.write(postData);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            // xử lý dữ liệu trả về
            InputStream responseBody = conn.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

            // Xử lý Json trong java
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject();
            String quote = getValue("message", jsonReader);
            resultText.setText(quote);

        } else {
            // TODO: Xử lý kết quả trả về là lỗi
        }
    }

    // TODO: tạo get request http://103.118.28.46:3000/get-list-quote trả về array (phải sử dụng JsonArray)
    // in ra màn hình (chú ý về độ trễ trả về)

    // function get và xử lý dữ liệu tra về
    private String getValue(String key, JsonReader jsonReader) throws Exception{
        String value = "";
        while (jsonReader.hasNext()){ //đọc json cho đến heets thì thôi
            String k = jsonReader.nextName();
            if (k.equals(key)){ // nếu key trong data là key đang tìm kiếm
                value = jsonReader.nextString(); // lấy giá trị
                break;
            } else {
                jsonReader.skipValue();
            }
        }
        return value;
    }

    public void onClickSendGetHttpUrlConnection(View view){
        try {
            sendGetHttpUrlConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickSendPostHttpUrlConnection(View view){
        try {
            sendPostHttpURLConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}