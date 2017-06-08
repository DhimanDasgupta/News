package com.dhimandasgupta.news.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhimandasgupta.news.R;
import com.dhimandasgupta.news.models.NewsArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsArticle> mArticles = new ArrayList<>();

    public NewsAdapter() {

    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.adapter_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(mArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void addArticles(List<NewsArticle> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDescription;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            mDescription = (TextView) itemView.findViewById(R.id.text_view_description);
        }

        public void bind(final NewsArticle article) {
            mTitle.setText(article.getTitle());
            mDescription.setText(article.getDescription());
        }
    }
}
