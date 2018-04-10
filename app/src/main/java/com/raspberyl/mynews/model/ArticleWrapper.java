package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleWrapper {

    @SerializedName("results")
    @Expose
    private List<Article> results;



    // Constructor
    public ArticleWrapper (List<Article> results) {
        this.results = results;

    }


    // Getter & setter
    public List<Article> getResults() {

        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }


}
