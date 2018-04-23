package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Docs {


    @SerializedName("web_url")
    @Expose

    private String web_url;

    @SerializedName("snippet")
    @Expose

    private String snippet;

    @SerializedName("pub_date")
    @Expose

    private String pub_date;

    @SerializedName("new_desk")
    @Expose

    private String new_desk;

    @SerializedName("section_name")
    @Expose

    private String section_name;


    @SerializedName("multimedia")
    @Expose

    private List<Multimedia> multimedia;

    // Constructors

    public Docs(String web_url, String snippet, String pub_date, String new_desk, String section_name, List<Multimedia> multimedia) {
        this.web_url = web_url;
        this.snippet = snippet;
        this.pub_date = pub_date;
        this.new_desk = new_desk;
        this.section_name = section_name;
        this.multimedia = multimedia;

    }

    // Getters & setters

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }


    public String getNew_desk() {
        return new_desk;
    }

    public void setNew_desk(String new_desk) {
        this.new_desk = new_desk;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }



    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }




}