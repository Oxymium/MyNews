package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asuka on 04/04/2018.
 */

public class ResponseWrapper {

    @SerializedName("response")
    @Expose
    private Response response;



    // Constructor
    public ResponseWrapper (Response response) {
        this.response = response;

    }


    // Getter & setter
    public Response getResponse() {

        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


}
