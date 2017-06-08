package com.dhimandasgupta.news.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.dhimandasgupta.news.livedata.NewsSourceLiveData;
import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.repo.NewsRepositoryImpl;

/**
 * Created by dhimandasgupta on 03/06/17.
 */

public class SingleActivityViewModel extends ViewModel {
    /**
     * Holds the Data in a LiveData class so that we can get the changes when the data
     * change.
     * */
    private NewsSourceLiveData liveData;

    /**
     * @{@link NewsRepositoryImpl} 's responsibility is to load the data
     * */
    private NewsRepositoryImpl newsRepository;

    public SingleActivityViewModel() {
        newsRepository = new NewsRepositoryImpl();
    }

    /**
     * Load the data is already not loaded
     * */
    public NewsSourceLiveData loadNews(News news) {
        if (liveData == null) {
            liveData = newsRepository.getNewsSourceSync(news);
        }

        return liveData;
    }

    /**
     * Forcefully Load the Data
     * */
    public NewsSourceLiveData forceLoadNews(News news) {
        liveData = newsRepository.getNewsSourceSync(news);
        return liveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        liveData = null;
        newsRepository = null;
    }

    public NewsSourceLiveData getLiveData() {
        return liveData;
    }
}
