package com.dhimandasgupta.news.ui.activity;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dhimandasgupta.news.R;
import com.dhimandasgupta.news.ui.adapter.NewsPagerAdapter;
import com.dhimandasgupta.news.utils.NewsCreator;
import com.dhimandasgupta.news.viewmodel.ViewPagerActivityViewModel;

public class ViewPagerExampleActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private NewsPagerAdapter mNewsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelProviders.of(this).get(ViewPagerActivityViewModel.class);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mNewsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), NewsCreator.getNews());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mNewsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
