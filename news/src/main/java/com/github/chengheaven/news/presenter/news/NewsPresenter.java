package com.github.chengheaven.news.presenter.news;

import com.github.chengheaven.news.data.DataRepository;

import javax.inject.Inject;

public class NewsPresenter implements NewsContract.Presenter {
    private final NewsContract.View mNewsView;
    private final DataRepository mDataRepository;

    @Inject
    NewsPresenter(NewsContract.View mNewsView, DataRepository mDataRepository) {
        this.mNewsView = mNewsView;
        this.mDataRepository = mDataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mNewsView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
