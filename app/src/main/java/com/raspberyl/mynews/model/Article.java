package com.raspberyl.mynews.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

// Article model type

public class Article {

    // Serialized JSON names

    @SerializedName("subsection")
    @Expose

    private String subsection;

    @SerializedName("section")
    @Expose

    private String section;

    @SerializedName("title")
    @Expose

    private String title;


    @SerializedName("published_date")
    @Expose

    private String published_date;


    @SerializedName("multimedia")
    @Expose

    private List<Multimedia> multimedia;


    @SerializedName("short_url")
    @Expose

    private String short_url;


    // Constructors

    public Article(String subsection, String section, String title, String published_date, List<Multimedia> multimedia, String short_url) {
        this.subsection = subsection;
        this.section = section;
        this.title = title;
        this.published_date = published_date;
        this.multimedia = multimedia;
        this.short_url = short_url;

    }

    // Getters & setters

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) { this.published_date = published_date; }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }



    // Display String as「[Section} > [Subsection}」, and only 「Section」 if subsection is empty

    public String getSection_Subsection() {

        String section_subsection = section + " > " + subsection;
        String section_simple = section;

        if (subsection.isEmpty()) {
            return section_simple;
        }else{
            return section_subsection;
        }
    }


    // Convert base date format input 「yyyy-MM-dd'T'HH:mm:ssXXX」 to desired date format 「dd"+"/"+"MM"+"/"+"yyyy」
    // Example: 「2018-03-23T05:00:07-04:00」 to 「23/03/2018」

    public String getPublished_date_converted() {
        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd"+"/"+"MM"+"/"+"yy");
        String resultDate = "";
        try {
            resultDate = outputFormat.format(inputFormat.parse(published_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }
}
