package com.dhimandasgupta.news.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.ui.fragment.NewsFragment;

import java.util.List;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class NewsPagerAdapter extends FragmentStatePagerAdapter {
    final private List<News> mNews;

    public NewsPagerAdapter(FragmentManager fm, @NonNull List<News> newses) {
        super(fm);
        mNews = newses;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(mNews.get(position));
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNews.get(position).getName();
    }
}
