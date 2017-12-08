package com.github.chengheaven.news.di.module;

import com.github.chengheaven.news.presenter.news.NewsContract;

import dagger.Module;
import dagger.Provides;

@Module public class NewsModule {
    private final NewsContract.View mView;

    public NewsModule(NewsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    NewsContract.View provideNewsContractView() {
        return mView;
    }
}
