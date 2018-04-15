package com.raspberyl.mynews.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.raspberyl.mynews.model.Article;
import com.raspberyl.mynews.model.MediaMetadata;
import com.raspberyl.mynews.utils.DateConvertUtils;
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
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.article_view, parent, false);
        MyViewHolder vHolder = new MyViewHolder(itemView);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Article article = mArticleList.get(position);


        // Display Section & Subsection in the following format: 「Section」>「Subsection」
        // If 「Subsection」doesn't exist, simply displays 「Section」
        if (article.getSubsection() == null) {
            holder.category.setText(article.getSection());
        }else{ holder.category.setText(article.getSection_Subsection()); }

        // Display「Title」in the View's body
        holder.description.setText(article.getTitle());
        //String testT = article.getMedia().get(0).getMediaMetadata().get(1).getUrl();
        //holder.description.setText(article.getMultimedia().get(0).getUrl());


        // Display「Date」in the View.
        // Output format should be「dd/MM/yy」 (for instance: 10/03/18 for 10th March 2018)
        holder.date.setText(DateConvertUtils.getPublished_date_converted(article.getPublished_date()));

        // Display image (load TopStories)
        if(article.getMultimedia() != null && article.getMultimedia().size() > 0) {
            String imageUrl = article.getMultimedia().get(0).getUrl();

            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get().load(imageUrl)
                        .resize(250,
                                250)
                        .into(holder.thumbnail);
            }
        }

        // Display image (load MostPopular)
        if(article.getMedia() != null && article.getMedia().size() > 0) {
            String imageUrl2 = article.getMedia().get(0).getMediaMetadata().get(1).getUrl();


            if (!TextUtils.isEmpty(imageUrl2)) {
                Picasso.get().load(imageUrl2)
                        .resize(250,
                                250)
                        .into(holder.thumbnail);
            }
        }

        // Onclicklistener
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "LOADING article", Toast.LENGTH_SHORT).show();

                // Pass URL to the WebView in a new Activity
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("WebViewUrl", article.getUrl());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}