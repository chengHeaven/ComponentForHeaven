package com.github.chengheaven.movie.di.module;

import com.github.chengheaven.movie.presenter.MovieContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/27.
 */
@Module
public class MovieModule {

    private final MovieContract.View mView;

    public MovieModule(MovieContract.View view) {
        this.mView = view;
    }

    @Provides
    MovieContract.View provideMovieContractView() {
        return mView;
    }
}
