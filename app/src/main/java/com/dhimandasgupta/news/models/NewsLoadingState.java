package com.dhimandasgupta.news.models;

/**
 * Created by dhimandasgupta on 08/06/17.
 */

/**
 * This POJO determines the Loading state
 * */
public class NewsLoadingState {
    private boolean isBeforeLoading;
    private boolean isLoading;
    private boolean isAfterLoading;

    private NewsLoadingState() {

    }

    public boolean isBeforeLoading() {
        return isBeforeLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isAfterLoading() {
        return isAfterLoading;
    }

    public static NewsLoadingState beforeLoading() {
        final NewsLoadingState state = new NewsLoadingState();

        state.isAfterLoading = true;
        state.isLoading = false;
        state.isAfterLoading = false;

        return state;
    }

    public static NewsLoadingState loading() {
        final NewsLoadingState state = new NewsLoadingState();

        state.isAfterLoading = false;
        state.isLoading = true;
        state.isAfterLoading = false;

        return state;
    }

    public static NewsLoadingState afterLoading() {
        final NewsLoadingState state = new NewsLoadingState();

        state.isAfterLoading = false;
        state.isLoading = false;
        state.isAfterLoading = true;

        return state;
    }
}
