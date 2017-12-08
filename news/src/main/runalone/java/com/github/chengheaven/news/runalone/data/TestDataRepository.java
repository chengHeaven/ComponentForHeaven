package com.github.chengheaven.news.runalone.data;

import com.github.chengheaven.news.runalone.data.news.TestNewsDataSource;

import javax.inject.Inject;

public class TestDataRepository implements TestNewsDataSource {

    private final TestNewsDataSource mNewsDataSource;

    @Inject
    TestDataRepository(TestNewsDataSource newsDataSource) {
        this.mNewsDataSource = newsDataSource;
    }
}
