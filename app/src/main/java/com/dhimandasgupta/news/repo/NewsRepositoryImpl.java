package com.dhimandasgupta.news.repo;

import android.os.AsyncTask;

import com.dhimandasgupta.news.apis.NewsApi;
import com.dhimandasgupta.news.apis.NewsArticleService;
import com.dhimandasgupta.news.livedata.NewsSourceLiveData;
import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.models.NewsLoadingState;
import com.dhimandasgupta.news.models.NewsResponse;
import com.dhimandasgupta.news.models.NewsSuccessResponse;
import com.dhimandasgupta.news.utils.ErrorConverter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class NewsRepositoryImpl implements NewsRepository {

    /**
     * Asynchronously Load the data using Retrofit Asynchronous way
     * */
    @Override
    public NewsSourceLiveData getNewsSourceAsync(News news) {
        final NewsSourceLiveData liveData = new NewsSourceLiveData();
        final NewsResponse newsResponse = new NewsResponse();
        newsResponse.setState(NewsLoadingState.loading());

        /**
         * Posting this to UI Thread to start showing the progress
         * */
        liveData.postValue(newsResponse);

        final Retrofit retrofit = NewsApi.getRetrofit();
        final NewsArticleService service = retrofit.create(NewsArticleService.class);

        service.getAllNewsArticles(news.getSource()).enqueue(new Callback<NewsSuccessResponse>() {
            @Override
            public void onResponse(Call<NewsSuccessResponse> call, Response<NewsSuccessResponse> response) {
                newsResponse.setState(NewsLoadingState.afterLoading());
                if (response != null && response.isSuccessful()) {
                    newsResponse.setSuccess(response.body());
                }  else {
                    newsResponse.setError(ErrorConverter.convert(retrofit, response));
                }

                liveData.setValue(newsResponse);
            }

            @Override
            public void onFailure(Call<NewsSuccessResponse> call, Throwable t) {
                newsResponse.setState(NewsLoadingState.afterLoading());
                newsResponse.setError(ErrorConverter.getNoInternetError());
                liveData.setValue(newsResponse);
            }
        });

        return liveData;
    }

    /**
     * Synchronously Load the data using Retrofit Synchronous way
     * */
    @Override
    public NewsSourceLiveData getNewsSourceSync(News news) {
        final NewsSourceLiveData liveData = new NewsSourceLiveData();
        final NewsResponse newsResponse = new NewsResponse();

        final Retrofit retrofit = NewsApi.getRetrofit();
        final NewsArticleService service = retrofit.create(NewsArticleService.class);

        /**
         * Intentionally used AsyncTask to create some delay in the response
         * and also this do not create any memory leak as
         *
         * View Model -> Repo -> this
         *
         * Since View Model is Singleton in Nature
         * */
        new AsyncTask<News, Void, NewsResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                newsResponse.setState(NewsLoadingState.loading());

                /**
                 * Posting this to UI Thread to start showing the progress
                 * */
                liveData.postValue(newsResponse);
            }

            @Override
            protected NewsResponse doInBackground(News... params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final Call<NewsSuccessResponse> call = service.getAllNewsArticles(params[0].getSource());

                Response<NewsSuccessResponse> response = null;

                try {
                    response = call.execute();

                    if (response != null && response.isSuccessful()) {
                        newsResponse.setSuccess(response.body());
                    }  else {
                        newsResponse.setError(ErrorConverter.convert(retrofit, response));
                    }
                } catch (IOException e) {
                    newsResponse.setError(ErrorConverter.getNoInternetError());
                }
                newsResponse.setState(NewsLoadingState.afterLoading());

                return newsResponse;
            }

            @Override
            protected void onPostExecute(NewsResponse newsResponse) {
                super.onPostExecute(newsResponse);

                liveData.setValue(newsResponse);
            }
        }.execute(news);

        return liveData;
    }
}
