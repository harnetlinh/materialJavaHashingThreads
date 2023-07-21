package com.example.myheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView superListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superListView = findViewById(R.id.mysuperheroes_list);

        getSuperHeroes();
    }

    private void getSuperHeroes(){
        Call<List<Model>> call =  RetrofitClient.getInstance()
                .getMyApi()
                .getsuperHeroes();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> myHeroList = response.body();
                String[] oneHeroes = new String[myHeroList.size()];
                for (int i = 0; i < myHeroList.size(); i++) {
                    oneHeroes[i] = myHeroList.get(i).getName();
                }

                // đẩy dữ liệu vào list view
                superListView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, oneHeroes));
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}