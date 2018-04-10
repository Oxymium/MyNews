package com.raspberyl.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Docs {


    @SerializedName("web_url")
    @Expose

    private String web_url;

    @SerializedName("source")
    @Expose

    private String source;

    @SerializedName("section_name")
    @Expose

    private String section_name;


    @SerializedName("subsection_name")
    @Expose

    private String subsection_name;

    @SerializedName("pub_date")
    @Expose

    private String pub_date;

    @SerializedName("multimedia")
    @Expose

    private List<Multimedia> multimedia;

    // Constructors


    public Docs(String web_url, String source, String section_name, String subsection_name, String pub_date, List<Multimedia> multimedia) {
        this.web_url = web_url;
        this.source = source;
        this.section_name = section_name;
        this.subsection_name = subsection_name;
        this.pub_date = pub_date;
        this.multimedia = multimedia;

    }

    // Getters & setters

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSubsection_name() {
        return subsection_name;
    }

    public void setSubsection_name(String subsection_name) {
        this.subsection_name = subsection_name;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

}