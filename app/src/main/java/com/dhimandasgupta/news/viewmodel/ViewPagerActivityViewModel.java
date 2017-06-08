package com.dhimandasgupta.news.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.v4.util.ArrayMap;

import com.dhimandasgupta.news.livedata.NewsSourceLiveData;
import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.repo.NewsRepositoryImpl;

import java.util.Map;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class ViewPagerActivityViewModel extends ViewModel {
    /**
     * Holds the Data in A Map for Each Fragment
     * */
    private Map<String, NewsSourceLiveData> liveDatum = new ArrayMap<>();

    /**
     * @{@link NewsRepositoryImpl} 's responsibility is to load the data
     * */
    private NewsRepositoryImpl newsRepository;

    public ViewPagerActivityViewModel() {
        newsRepository = new NewsRepositoryImpl();
    }

    public NewsSourceLiveData loadNews(News news) {
        /**
         * Load the Data if the data is already not loaded
         * OR
         * The Cached data is Error data
         * */

        if (liveDatum.get(news.getSource()) == null ||
                (liveDatum.get(news.getSource()) != null && !liveDatum.get(news.getSource()).getValue().isSuccessful())) {
            final NewsSourceLiveData liveData = newsRepository.getNewsSourceAsync(news);
            liveDatum.put(news.getSource(), liveData);
        }

        return liveDatum.get(news.getSource());
    }

    public NewsSourceLiveData forceLoadNews(News news) {
        /**
         * Load the data is already not loaded
         * */
        if (liveDatum.get(news.getSource()) == null) {
            return loadNews(news);
        }

        /**
         * Forcefully Loading the Data
         * */
        final NewsSourceLiveData liveData = newsRepository.getNewsSourceAsync(news);
        liveDatum.put(news.getSource(), liveData);
        return liveDatum.get(news.getSource());
    }
}
