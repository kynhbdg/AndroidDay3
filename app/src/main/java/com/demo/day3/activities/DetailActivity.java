package com.demo.day3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.demo.day3.AppDatabase;
import com.demo.day3.BookmarkEntity;
import com.demo.day3.R;
import com.demo.day3.model.Item;
import com.demo.day3.network.ApiManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {
    Item model;
    AppDatabase db;
    FloatingActionButton btBookmark;
    Boolean isBookmark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        db = AppDatabase.getAppDatabase(this);

        btBookmark = findViewById(R.id.btBookmark);
        btBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBookmark) {
                    deleteBookmark();
                    btBookmark.setImageResource(R.drawable.ic_bookmark);
                } else {
                    addBookmark();
                    btBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }
                isBookmark = !isBookmark;
            }
        });

        initView();
        checkBookmark();
    }

    private void initView() {
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String link = ApiManager.SERVER_URL + "/" + url;

        model = (Item) getIntent().getSerializableExtra("ITEM");

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addBookmark() {
        BookmarkEntity bookmarkEntity = new BookmarkEntity();
        bookmarkEntity.id = model.getId();
        bookmarkEntity.title = model.getTitle();
        bookmarkEntity.date = model.getDate();
        bookmarkEntity.content = model.getContent().getDescription();
        bookmarkEntity.image = model.getImage();
        bookmarkEntity.url = model.getContent().getUrl();

        long id = db.bookmarkDao().insertBookmark(bookmarkEntity);
        Toast.makeText(this, "Success " + id, Toast.LENGTH_SHORT).show();
    }

    private void deleteBookmark() {
        BookmarkEntity bookmarkEntity = db.bookmarkDao().getBookmark(model.getId());
        db.bookmarkDao().deleteBookmark(bookmarkEntity);
    }

    private void checkBookmark() {
        BookmarkEntity bookmarkEntity = db.bookmarkDao().getBookmark(model.getId());
        if (bookmarkEntity != null) {
            btBookmark.setImageResource(R.drawable.ic_bookmark_fill);
            isBookmark = true;
        } else {
            btBookmark.setImageResource(R.drawable.ic_bookmark);
            isBookmark = false;
        }
    }
}