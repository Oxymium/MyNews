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

    @SerializedName("document_type")
    @Expose

    private String document_type;

    @SerializedName("source")
    @Expose

    private String source;

    @SerializedName("multimedia")
    @Expose

    private List<Multimedia> multimedia;

    // Constructors

    public Docs(String web_url, String snippet, String pub_date, String document_type, String source, List<Multimedia> multimedia) {
        this.web_url = web_url;
        this.snippet = snippet;
        this.pub_date = pub_date;
        this.document_type = document_type;
        this.source = source;
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

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    // Method to returns 「Source」 > 「Article」 format
    public String getSource_documentType() {
        StringBuilder stringBuilder = new StringBuilder(document_type);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        String document_typeUpper = stringBuilder.toString();
        String outputString = source + " > " + document_typeUpper;
        return outputString;
    }


}