package com.github.chengheaven.technology.di.component;

import com.github.chengheaven.technology.data.DataRepository;
import com.github.chengheaven.technology.di.module.ApplicationModule;
import com.github.chengheaven.technology.di.module.DataRepositoryModule;

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
