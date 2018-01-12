package com.github.chengheaven.movie.di.component;

import com.github.chengheaven.movie.data.DataRepository;
import com.github.chengheaven.movie.di.module.ApplicationModule;
import com.github.chengheaven.movie.di.module.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Heaven_cheng
 */
@Singleton
@Component(modules = {DataRepositoryModule.class, ApplicationModule.class})
public interface DataRepositoryComponent {

    /**
     * 获取数据仓库
     */
    DataRepository getRepository();
}
