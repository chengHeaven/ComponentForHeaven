package com.github.chengheaven.news.runalone.di.component;

import com.github.chengheaven.news.runalone.data.TestDataRepository;
import com.github.chengheaven.news.runalone.di.module.ApplicationModule;
import com.github.chengheaven.news.runalone.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface TestDataRepositoryComponent {
    TestDataRepository getRepository();
}
