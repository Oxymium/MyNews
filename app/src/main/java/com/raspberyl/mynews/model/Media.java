package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Media {

    @SerializedName("media-metadata")
    @Expose

    private List<MediaMetadata>  mediaMetadata;

    // Constructor

    public Media(List<MediaMetadata> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }


    // Getter & setter

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadata> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }


}
