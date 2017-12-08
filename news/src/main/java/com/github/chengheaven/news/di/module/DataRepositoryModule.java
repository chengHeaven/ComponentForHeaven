package com.github.chengheaven.news.di.module;

import android.content.Context;

import com.github.chengheaven.news.data.news.NewsDataSource;
import com.github.chengheaven.news.data.news.NewsDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    NewsDataSource provideNewsDataSource(Context context) {
        return new NewsDataSourceImpl(context);
    }
}
