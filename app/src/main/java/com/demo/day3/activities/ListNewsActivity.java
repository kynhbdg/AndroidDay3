package com.demo.day3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.demo.day3.AppDatabase;
import com.demo.day3.BookmarkEntity;
import com.demo.day3.R;
import com.demo.day3.adapter.NewsAdapter;
import com.demo.day3.interfaces.NewsOnClick;
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
    AppDatabase db;

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

        adapter.setiOnClick(new NewsOnClick() {
            @Override
            public void onCLickItem(int position) {
                Item model = listData.get(position);
                Intent intent = new Intent(ListNewsActivity.this, DetailActivity.class);
                intent.putExtra("URL", model.getContent().getUrl());
                intent.putExtra("ITEM", model);
                startActivity(intent);
            }
        });

        db = AppDatabase.getAppDatabase(this);

//        insertBookmark();
//        updateBookmark(1);
//        getAllBookmark();
//        findBookmark(1);

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

    private void insertBookmark() {
        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.title = "This is title";
        bookmarkEntity.content = "This is content";
        db.bookmarkDao().insertBookmark(bookmarkEntity);
    }

    private void updateBookmark(int id) {
        BookmarkEntity bookmarkEntity = db.bookmarkDao().getBookmark(id);
        bookmarkEntity.title = "This is title update";
        db.bookmarkDao().updateBookmark(bookmarkEntity);
    }

    private void findBookmark(int id) {
        BookmarkEntity bookmarkEntity = db.bookmarkDao().getBookmark(id);
        Log.d("TAG", "Find Bookmark with id: " + bookmarkEntity.id + ", title: " + bookmarkEntity.title);
    }

    private void deleteBookmark(int id) {
        BookmarkEntity bookmarkEntity = db.bookmarkDao().getBookmark(id);
        db.bookmarkDao().deleteBookmark(bookmarkEntity);
    }

    private void deleteAllBookmark() {
        db.bookmarkDao().deleteAll();
    }

    private void getAllBookmark() {
        List<BookmarkEntity> list = db.bookmarkDao().getAllBookmark();
        for (BookmarkEntity model : list) {
            Log.d("TAG", "id: " + model.id + ", title: " + model.title);
        }
    }
}