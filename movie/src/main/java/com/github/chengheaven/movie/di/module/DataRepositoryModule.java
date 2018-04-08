package com.github.chengheaven.movie.di.module;

import android.content.Context;

import com.github.chengheaven.movie.data.movie.MovieDataSource;
import com.github.chengheaven.movie.data.movie.MovieDataSourceImpl;

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
    MovieDataSource provideMovieDataSource(Context context) {
        return new MovieDataSourceImpl(context);
    }
}
