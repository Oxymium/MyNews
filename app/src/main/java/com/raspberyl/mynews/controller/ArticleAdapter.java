package com.raspberyl.mynews.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import com.raspberyl.mynews.model.Article;
import com.squareup.picasso.Picasso;
import com.raspberyl.mynews.R;

import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> mArticleList;
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

    public ArticleAdapter(List<Article> mArticleList, Context mContext) {
        this.mArticleList = mArticleList;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Article article = mArticleList.get(position);

        // Display section
        // holder.category.setText(article.getSection() + " > " + article.getSubsection());
        holder.category.setText(article.getSection_Subsection());
        // Display content
        holder.description.setText(article.getTitle());

        // Display converted date
        // DATE FORMAT: 2018-03-23T05:00:07-04:00
        holder.date.setText(article.getPublished_date_converted());

        // Display image
        if(article.getMultimedia() != null && article.getMultimedia().size() > 0) {
            String imageUrl = article.getMultimedia().get(0).getUrl();
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get().load(imageUrl)
                        .resize(200, 200)
                        .into(holder.thumbnail);
            }
        }

        // Onclicklistener
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "LOAD article", Toast.LENGTH_SHORT).show();

                // Pass URL to the WebView in a new Activity
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("WebViewUrl", article.getShort_url());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}