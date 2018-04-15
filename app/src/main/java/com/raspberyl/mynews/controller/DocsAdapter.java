package com.raspberyl.mynews.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raspberyl.mynews.R;
import com.raspberyl.mynews.model.Article;
import com.raspberyl.mynews.model.Docs;
import com.raspberyl.mynews.utils.DateConvertUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.MyViewHolder> {

    private List<Docs> mDocsList;
    private Context mContext;
    // Img test


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category, description, date;
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            category = view.findViewById(R.id.article_category);
            description = view.findViewById(R.id.article_content);
            date = view.findViewById(R.id.article_date);
            thumbnail = view.findViewById(R.id.article_thumbnail);
        }

    }

    public DocsAdapter(List<Docs> mDocsList, Context mContext) {
        this.mDocsList = mDocsList;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.article_view, parent, false);
        MyViewHolder vHolder = new MyViewHolder(itemView);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Docs docs = mDocsList.get(position);


        // Display「Date」in the View.
        // Output format should be「dd/MM/yy」 (for instance: 10/03/18 for 10th March 2018)
        holder.description.setText(docs.getSnippet());

    }

    @Override
    public int getItemCount() {
        return mDocsList.size();
    }
}
