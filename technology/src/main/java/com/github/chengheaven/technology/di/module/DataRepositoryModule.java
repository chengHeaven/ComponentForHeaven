package com.github.chengheaven.technology.di.module;

import android.content.Context;

import com.github.chengheaven.technology.data.technology.TechnologyDataSource;
import com.github.chengheaven.technology.data.technology.TechnologyDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    TechnologyDataSource provideCodeDataSource(Context context) {
        return new TechnologyDataSourceImpl(context);
    }
}
