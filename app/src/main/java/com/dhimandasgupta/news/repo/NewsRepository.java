package com.dhimandasgupta.news.repo;

import com.dhimandasgupta.news.livedata.NewsSourceLiveData;
import com.dhimandasgupta.news.models.News;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public interface NewsRepository {
    NewsSourceLiveData getNewsSourceAsync(News news);
    NewsSourceLiveData getNewsSourceSync(News news);
}
