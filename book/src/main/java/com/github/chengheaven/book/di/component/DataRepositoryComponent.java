package com.github.chengheaven.book.di.component;

import com.github.chengheaven.book.data.DataRepository;
import com.github.chengheaven.book.di.module.ApplicationModule;
import com.github.chengheaven.book.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface DataRepositoryComponent {

    DataRepository getRepository();
}
