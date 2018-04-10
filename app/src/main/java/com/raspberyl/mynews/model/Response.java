package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asuka on 05/04/2018.
 */

public class Response {

    @SerializedName("docs")
    @Expose

    private List<Docs> docs;

    // Constructor

    public Response(List<Docs> docs) {
        this.docs = docs;
    }

    // Getter & setter


    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }


}
