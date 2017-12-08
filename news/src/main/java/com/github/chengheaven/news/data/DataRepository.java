package com.github.chengheaven.news.data;

import com.github.chengheaven.news.data.news.NewsDataSource;

import javax.inject.Inject;

public class DataRepository implements NewsDataSource {

    private final NewsDataSource mNewsDataSource;

    @Inject
    DataRepository(NewsDataSource newsDataSource) {
        this.mNewsDataSource = newsDataSource;
    }
}
