package com.dhimandasgupta.news.livedata;

import android.arch.lifecycle.MediatorLiveData;

import com.dhimandasgupta.news.models.NewsResponse;

/**
 * Created by dhimandasgupta on 03/06/17.
 */

/**
 * Object To be used in ViewModel/ ViewModels
 *
 * Not necessary, we can also use LiveData<NewsResponse> instead
 * of this class
 * */
public class NewsSourceLiveData extends MediatorLiveData<NewsResponse> {

}
