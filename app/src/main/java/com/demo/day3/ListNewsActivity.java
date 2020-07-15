package com.demo.day3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.demo.day3.adapter.NewsAdapter;
import com.demo.day3.model.Item;
import com.demo.day3.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListNewsActivity extends AppCompatActivity {
    RecyclerView rvListNews;
    List<Item> listData;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        // B1: Datasource
        getListData();

        // B2: Adapter
        listData = new ArrayList<>();
        adapter = new NewsAdapter(ListNewsActivity.this, listData);

        // B3: LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        // B4: RecyclerView
        rvListNews = findViewById(R.id.rvListNews);
        rvListNews.setLayoutManager(layoutManager);
        rvListNews.setAdapter(adapter);
    }

    private void getListData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiManager apiManager = retrofit.create(ApiManager.class);
        apiManager.getListData().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.body() != null) {
                    listData = response.body();
                    adapter.reloadData(listData);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(ListNewsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}