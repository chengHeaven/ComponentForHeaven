package com.github.chengheaven.news.di.component;

import com.github.chengheaven.news.data.DataRepository;
import com.github.chengheaven.news.di.module.ApplicationModule;
import com.github.chengheaven.news.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface DataRepositoryComponent {
    DataRepository getRepository();
}
