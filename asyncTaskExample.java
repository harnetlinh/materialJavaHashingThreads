package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button _myButton;
    private TextView _textNumber;

    private Handler _handler;

    private Integer messageNumber;

    private static final int MESSAGE_DONE = 101;

    private static final int MESSAGE_PROCESS = 100;

    private boolean isProcessing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        listenerHandler();
        Start();
    }

    protected void Init(){
        _textNumber = findViewById(R.id.text_number);
        _myButton = findViewById(R.id.button_count);
        _textNumber.setText("0");
    }

    private void countNumber(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++){
                    Message message = new Message();
                    message.what = MESSAGE_PROCESS;
                    message.arg1 = i;
                    _handler.sendMessage(message);
                    if ( i == 10){
                        isProcessing = false;
                    }
                    if (!isProcessing){
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
                _handler.sendEmptyMessage(MESSAGE_DONE);
            }
        }).start();
    }

    private void listenerHandler(){
        _handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case MESSAGE_DONE:
                        _textNumber.setText("thread is DONE");
                        break;
                    case MESSAGE_PROCESS:
                        _textNumber.setText("thread is running " + String.valueOf(msg.arg1));
                        break;

                    default:
                        _textNumber.setText("Undefined message");
                        break;
                }
            }
        };
    }

    public void Start(){
        countNumber();
    }

}