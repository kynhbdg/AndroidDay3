package com.demo.day3;

import com.demo.day3.model.Item;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {
    String SERVER_URL = "https://api-demo-anhth.herokuapp.com";

    @GET("data.json")
    Call<Item>getItemData();
}
