package com.dhimandasgupta.news.models;

/**
 * Created by dhimandasgupta on 03/06/17.
 */

/**
 * /**
 * This POJO used to represent the Success Response and Failure Response and Loading state from News API
 * */
public class NewsResponse {
    private NewsSuccessResponse success;
    private NewsFailureResponse error;

    private NewsLoadingState state = NewsLoadingState.beforeLoading();

    public NewsSuccessResponse getSuccess() {
        return success;
    }

    public void setSuccess(NewsSuccessResponse success) {
        this.success = success;
    }

    public NewsFailureResponse getError() {
        return error;
    }

    public void setError(NewsFailureResponse error) {
        this.error = error;
    }

    public boolean isSuccessful() {
        return success != null && error == null;
    }

    public NewsLoadingState getState() {
        return state;
    }

    public void setState(NewsLoadingState state) {
        this.state = state;
    }
}
