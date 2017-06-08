package com.dhimandasgupta.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dhimandasgupta on 03/06/17.
 */

/**
 * This POJO used to represent the Failure Response from News API
 * */
public class NewsFailureResponse {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("message")
    @Expose
    public String message;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "NewsFailureResponse{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static NewsFailureResponse getDefaultError() {
        final NewsFailureResponse error = new NewsFailureResponse();

        error.status = "Error";
        error.code = "Unknown";
        error.message = "Unable to Parse Error response";

        return error;
    }

    public static NewsFailureResponse getNoInternetError() {
        final NewsFailureResponse error = new NewsFailureResponse();

        error.status = "Error";
        error.code = "Unknown";
        error.message = "No Internet Error";

        return error;
    }
}
