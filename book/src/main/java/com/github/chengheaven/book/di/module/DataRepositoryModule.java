package com.github.chengheaven.book.di.module;

import android.content.Context;

import com.github.chengheaven.book.data.book.BookDataSource;
import com.github.chengheaven.book.data.book.BookDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    BookDataSource provideBookDataSource(Context context) {
        return new BookDataSourceImpl(context);
    }
}
