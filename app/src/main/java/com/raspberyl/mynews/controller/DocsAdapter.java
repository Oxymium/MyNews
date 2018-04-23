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
import com.raspberyl.mynews.utils.CategoryReformatUtils;
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

        // holder.category.setText(docs.getNewdesk_sectionName());
        holder.category.setText(CategoryReformatUtils.reformatCategory(docs.getNew_desk(), docs.getSection_name()));

        holder.description.setText(docs.getSnippet());

        // Display「Date」in the View.
        // Output format should be「dd/MM/yy」 (for instance: 10/03/18 for 10th March 2018)
        holder.date.setText(DateConvertUtils.getPublished_date_converted(docs.getPub_date()));

        // Onclicklistener
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, docs.getWeb_url(), Toast.LENGTH_SHORT).show();

                // Pass URL to the WebView in a new Activity
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("WebViewUrl", docs.getWeb_url());
                mContext.startActivity(intent);
            }
        });

        if(docs.getMultimedia() != null &&  docs.getMultimedia().size() > 0 ) {

            String imageUrl;
            // Tries to get the standard image URL (item 2), but older articles have only 1 or 2 images
            try {
                imageUrl = docs.getMultimedia().get(2).getUrl();
            }catch (IndexOutOfBoundsException e) {
                imageUrl = docs.getMultimedia().get(0).getUrl();
            }


                if (!TextUtils.isEmpty(imageUrl)) {
                    Picasso.get().load("https://www.nytimes.com/"+ imageUrl)
                            .resize(200, 200)
                            .into(holder.thumbnail);
                }
            }


    }

    @Override
    public int getItemCount() {
        return mDocsList.size();
    }
}


