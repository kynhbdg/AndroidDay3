package com.demo.day3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.day3.R;
import com.demo.day3.model.Item;
import com.demo.day3.network.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvDate, tvTitle, tvContent;
    ImageView ivCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = findViewById(R.id.tvDate);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        ivCover = findViewById(R.id.ivCover);

        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager apiManager = retrofit.create(ApiManager.class);
        apiManager.getItemData().enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.body() == null) {
                    return;
                }
                Item model = response.body();
                tvTitle.setText(model.getTitle());
                tvDate.setText(model.getDate());
                tvContent.setText(model.getContent().getDescription());
                Glide.with(MainActivity.this).load(model.getImage()).into(ivCover);

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t);
            }
        });


    }
}