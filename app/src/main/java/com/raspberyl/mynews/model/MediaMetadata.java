package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Asuka on 04/04/2018.
 */

public class MediaMetadata {

    @SerializedName("url")
    @Expose

    private String url;

    // Constructor

    public MediaMetadata(String url) {
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
