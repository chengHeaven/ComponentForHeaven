package com.github.chengheaven.movie.data.movie;

import com.github.chengheaven.movie.bean.MovieDetailBean;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public interface MovieDataSource {

    interface Callback<T> {

        void onSuccess(List<T> list);

        void onFailure(String msg);
    }

    interface DetailCallback {

        void onSuccess(MovieDetailBean bean);

        void onFailure(String msg);
    }

    /**
     * request hot movie list
     *
     * @param callback return movie List<T>
     */
    void getHotMovie(Callback callback);

    /**
     * request top COUNT movie list
     *
     * @param start    从第几个开始
     * @param count    获取多少条数据
     * @param callback return top movie List<T>
     */
    void getMovieTop(int start, int count, Callback callback);

    /**
     * request movie detail data
     *
     * @param id       movie id
     * @param callback movie detail data
     */
    void getMovieDetail(String id, DetailCallback callback);
}
