package com.dhimandasgupta.news.utils;

import com.dhimandasgupta.news.models.NewsFailureResponse;
import com.dhimandasgupta.news.models.NewsSuccessResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dhimandasgupta on 03/06/17.
 */

/**
 * Utility method to use convert error response to POJO class
 * */
public class ErrorConverter {
    public static NewsFailureResponse convert(Retrofit retrofit, Response<NewsSuccessResponse> response) {
        NewsFailureResponse newsSourceError;

        try {
            Converter<ResponseBody, NewsFailureResponse> errorConverter = retrofit.responseBodyConverter(NewsFailureResponse.class, new Annotation[0]);
            newsSourceError = errorConverter.convert(response.errorBody());
        } catch (IOException e) {
            newsSourceError = NewsFailureResponse.getDefaultError();
        }

        return newsSourceError;
    }

    public static NewsFailureResponse getNoInternetError() {
        return NewsFailureResponse.getNoInternetError();
    }
}
