package com.example.myheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://simplifiedcoding.net/";

    @GET("demos/marvel")
    Call<List<Model>> getsuperHeroes();

}
