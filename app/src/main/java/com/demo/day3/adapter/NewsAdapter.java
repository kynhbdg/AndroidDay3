package com.demo.day3.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.day3.R;
import com.demo.day3.model.Item;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Item> list;

    public NewsAdapter(Activity activity, List<Item> list) {
        this.activity = activity;
        this.list = list;
    }

    public void reloadData(List<Item> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_news, parent, false);
        NewsHolder holder = new NewsHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsHolder hd = (NewsHolder) holder;
        Item model = list.get(position);
        hd.tvDate.setText(model.getDate());
        hd.tvTitle.setText(model.getTitle());
        hd.tvContent.setText(model.getContent().getDescription());
        Glide.with(activity).load(model.getImage()).into(hd.ivCover);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTitle, tvContent;
        ImageView ivCover;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivCover = itemView.findViewById(R.id.ivCover);
        }
    }
}


