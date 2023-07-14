package com.example.lab3;

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
    //
    private static final String GET_URL = "http://10.82.1.4:3000/get-quote";

    private static final String POST_URL = "http://10.82.1.4:3000/add-quote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.result);
    }

    private void sendGetHttpURLConnection() throws  Exception{
        URL url = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream responseBody = con.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject(); // Start processing the JSON object
            resultText.setText(getValue("quote", jsonReader));
        } else {
            System.out.println("ERROR");
        }
        con.disconnect();
    }

    // send POST request
    private void sendPostHttpURLConnection() throws Exception{
        URL url = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);
        JSONObject data = new JSONObject();
        data.put("name", "Linh");


        byte[] postData = data.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream outputStream = con.getOutputStream()) {
            outputStream.write(postData);
        }

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream responseBody = con.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject(); // Start processing the JSON object
            resultText.setText(getValue("message", jsonReader));
        } else {
            System.out.println("ERROR");
        }
        con.disconnect();
    }

    // function to get value of input key from JSON object
    private String getValue(String key, JsonReader jsonReader) throws Exception{
        String value = "";
        while (jsonReader.hasNext()) { // Loop through all keys
            String k = jsonReader.nextName(); // Fetch the next key
            if (k.equals(key)) { // Check if desired key
                // Fetch the value as a String
                value = jsonReader.nextString();
                break; // Break out of the loop
            } else {
                jsonReader.skipValue(); // Skip values of other keys
            }
        }
        return value;
    }

    private void sendGetRetrofit() throws Exception{

    }

    private void sendPostRetrofit() throws Exception{

    }

    // function send GET request with Retrofit library
    public void onClickSendGetRetrofit(View view) {
        try {
            sendGetRetrofit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // function send POST request with Retrofit library
    public void onClickSendPostRetrofit(View view) {
        try {
            sendPostRetrofit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickSendGetHttpURLConnection(View view) {
        try {
            sendGetHttpURLConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickSendPostHttpURLConnection(View view) {
        try {
            sendPostHttpURLConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}