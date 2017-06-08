package com.dhimandasgupta.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

/**
 * This POJO used to represent the Success Response from News API
 * */
public class NewsSuccessResponse {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("sortBy")
    @Expose
    public String sortBy;
    @SerializedName("articles")
    @Expose
    public List<NewsArticle> articles = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }
}
