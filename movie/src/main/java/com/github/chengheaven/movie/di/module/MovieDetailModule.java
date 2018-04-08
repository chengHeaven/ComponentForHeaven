package com.github.chengheaven.movie.di.module;

import com.github.chengheaven.movie.presenter.detail.MovieDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@Module
public class MovieDetailModule {

    private final MovieDetailContract.View mView;

    public MovieDetailModule(MovieDetailContract.View view) {
        this.mView = view;
    }

    @Provides
    MovieDetailContract.View provideMovieDetailContractView() {
        return mView;
    }
}
