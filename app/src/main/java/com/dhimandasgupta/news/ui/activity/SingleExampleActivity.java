package com.dhimandasgupta.news.ui.activity;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.dhimandasgupta.news.R;
import com.dhimandasgupta.news.models.News;
import com.dhimandasgupta.news.models.NewsResponse;
import com.dhimandasgupta.news.ui.adapter.NewsAdapter;
import com.dhimandasgupta.news.utils.NewsCreator;
import com.dhimandasgupta.news.viewmodel.SingleActivityViewModel;

public class SingleExampleActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    /**
     * View Model for the Activity
     * Which is a Singleton object
     * */
    private SingleActivityViewModel mViewModel;

    /**
     * Observer to monitor the UI changes, whenever data is pushed by the LiveData
     * */
    private Observer<NewsResponse> mObserver = new Observer<NewsResponse>() {
        @Override
        public void onChanged(@Nullable NewsResponse newsResponse) {
            updateUIWithLatestSource(newsResponse);
        }
    };

    /**
     * Below all member variables are used for UI purpose only
     * */
    private NewsAdapter mNewsAdapter;

    private RecyclerView mRecyclerView;
    private AppCompatTextView mErrorTextView;
    private AppCompatButton mButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_example);

        final News news = NewsCreator.getANews();

        mNewsAdapter = new NewsAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mNewsAdapter);

        mErrorTextView = (AppCompatTextView) findViewById(R.id.text_view_error);

        mButton = (AppCompatButton) findViewById(R.id.button);
        mButton.setText("Get News From " + news.getName());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicked(news);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mViewModel = ViewModelProviders.of(this).get(SingleActivityViewModel.class);
        if (mViewModel.getLiveData() != null) {
            mViewModel.getLiveData().observe(this, mObserver);
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    private void onClicked(News news) {
        // Force Load if data already loaded or failed
        if (mViewModel.getLiveData() != null) {
            mViewModel.forceLoadNews(news).observe(this, mObserver);
        } else {
            mViewModel.loadNews(news).observe(this, mObserver);
        }
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

    private void setLoading(boolean loading) {
        if (loading) {
            mRecyclerView.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mButton.setEnabled(false);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mButton.setEnabled(true);
        }
    }
}
