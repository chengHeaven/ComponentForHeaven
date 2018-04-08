package com.github.chengheaven.movie.di.module;

import com.github.chengheaven.movie.presenter.top.MovieTopContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@Module
public class MovieTopModule {

    private final MovieTopContract.View mView;

    public MovieTopModule(MovieTopContract.View view) {
        this.mView = view;
    }

    @Provides
    MovieTopContract.View provideMovieTopContractView() {
        return mView;
    }
}
