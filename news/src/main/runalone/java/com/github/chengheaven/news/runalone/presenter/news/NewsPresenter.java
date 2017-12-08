package com.github.chengheaven.news.runalone.presenter.news;

import com.github.chengheaven.news.runalone.data.TestDataRepository;

import javax.inject.Inject;

public class NewsPresenter implements NewsContract.Presenter {
    private final NewsContract.View mNewsView;
    private final TestDataRepository mDataRepository;

    @Inject
    NewsPresenter(NewsContract.View mNewsView, TestDataRepository mDataRepository) {
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
