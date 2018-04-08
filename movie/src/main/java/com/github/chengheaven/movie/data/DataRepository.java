package com.github.chengheaven.movie.data;

import com.github.chengheaven.movie.data.movie.MovieDataSource;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 2017/12/26.
 */
public class DataRepository implements MovieDataSource {

    private final MovieDataSource mMovieDataSource;

    @Inject
    DataRepository(MovieDataSource movieDataSource) {
        this.mMovieDataSource = movieDataSource;
    }

    @Override
    public void getHotMovie(Callback callback) {
        mMovieDataSource.getHotMovie(callback);
    }

    @Override
    public void getMovieTop(int start, int count, Callback callback) {
        mMovieDataSource.getMovieTop(start, count, callback);
    }

    @Override
    public void getMovieDetail(String id, DetailCallback callback) {
        mMovieDataSource.getMovieDetail(id, callback);
    }
}
