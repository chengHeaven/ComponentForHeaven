package com.github.chengheaven.news.runalone.di.module;

import android.content.Context;

import com.github.chengheaven.news.runalone.data.news.TestNewsDataSource;
import com.github.chengheaven.news.runalone.data.news.TestNewsDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    TestNewsDataSource provideTestNewsDataSource(Context context) {
        return new TestNewsDataSourceImpl(context);
    }
}
