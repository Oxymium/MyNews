package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Subcategory of the Article model (mainly the image URL)

public class Multimedia {

    @SerializedName("url")
    @Expose

    private String url;

    // Constructor

    public Multimedia(String url) {
        this.url = url;
    }

    // Getter & setter

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

