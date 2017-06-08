package com.dhimandasgupta.news.apis;

import com.dhimandasgupta.news.models.NewsSuccessResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public interface NewsArticleService {
    /**
     * Calling the news API endpoint to get the list of news from a particular source
     * */
    @GET("articles?&apiKey=" + NewsApi.NEWS_API_KEY)
    Call<NewsSuccessResponse> getAllNewsArticles(@Query("source") String source);
}