package com.dhimandasgupta.news.ui.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhimandasgupta.news.R;
import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.models.NewsResponse;
import com.dhimandasgupta.news.ui.adapter.NewsAdapter;
import com.dhimandasgupta.news.viewmodel.ViewPagerActivityViewModel;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class NewsFragment extends Fragment implements LifecycleRegistryOwner {
    private static final String NEWS_ARGUMENT = "news_argument";

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private News mNews;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private AppCompatTextView mErrorTextView;

    private NewsAdapter mNewsAdapter;

    /**
     * Manages the Data, this is a Activity View Model, since we need to cache the data
     * for all the fragments in the adapter
     * */
    private ViewPagerActivityViewModel mViewModel;

    /**
     * Monitors Data change
     * */
    private Observer<NewsResponse> mObserver = new Observer<NewsResponse>() {
        @Override
        public void onChanged(@Nullable NewsResponse source) {
            updateUIWithLatestSource(source);
        }
    };

    public static NewsFragment newInstance(final News news) {
        final Bundle args = new Bundle();
        args.putParcelable(NEWS_ARGUMENT, news);

        final NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(args);

        return newsFragment;
    }

    public NewsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNews = getArguments().getParcelable(NEWS_ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    forceLoad();
                }
            }
        });

        mNewsAdapter = new NewsAdapter();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mNewsAdapter);

        mErrorTextView = (AppCompatTextView) view.findViewById(R.id.text_view_error);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Usage the Activity View Model instead of its Own View Model, Since this Fragment lives in A View Pager.
         * */
        mViewModel = ViewModelProviders.of(getActivity()).get(ViewPagerActivityViewModel.class);
        load();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    /**
     * Request to load the data
     * */
    private void load() {
        mViewModel.loadNews(mNews).observe(this, mObserver);
    }

    /**
     * Request to load the data forcefully
     * */
    private void forceLoad() {
        mViewModel.forceLoadNews(mNews).observe(this, mObserver);
    }

    /**
     * Simply Updates the UI, no other responsibility
     * */
    private void updateUIWithLatestSource(NewsResponse source) {
        setLoading(source.getState().isLoading());

        if (source.getState().isAfterLoading()) {
            if (source.getSuccess() != null) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mNewsAdapter.addArticles(source.getSuccess().getArticles());
                mErrorTextView.setVisibility(View.GONE);
            } else if (source.getError() != null) {
                mRecyclerView.setVisibility(View.GONE);
                mErrorTextView.setText(source.getError().getMessage());
                mErrorTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Simply Updates the UI, no other responsibility
     * */
    private void setLoading(boolean loading) {
        mSwipeRefreshLayout.setRefreshing(loading);
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
    }
}
