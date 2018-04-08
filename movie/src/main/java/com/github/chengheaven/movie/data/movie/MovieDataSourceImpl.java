package com.github.chengheaven.movie.data.movie;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */

import android.content.Context;

import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.bean.MovieDetailBean;
import com.github.chengheaven.movie.data.RetrofitFactory;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MovieDataSourceImpl implements MovieDataSource {

    private Context mContext;

    public MovieDataSourceImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getHotMovie(Callback callback) {

        final int[] retryCount = {5};

        Observable<MovieBean> observable = RetrofitFactory.getDouBanInstance().requestHotMovie();
        observable
                .retryWhen(observable1 -> observable1.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                    if (throwable instanceof UnknownHostException) {
                        return Observable.error(throwable);
                    }
                    if (retryCount[0] > 0) {
                        retryCount[0]--;
                        return Observable.timer(2000, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error((Throwable) throwable);
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieBean list) {
                        if (list != null) {
                            callback.onSuccess(list.getSubjects());
                        }
                    }
                });
    }

    @Override
    public void getMovieTop(int start, int count, Callback callback) {
        final int[] retryCount = {5};
        Observable<MovieBean> observable = RetrofitFactory.getDouBanInstance().requestMovieTop250(start, count);
        observable
                .retryWhen(observable1 -> observable1.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                    if (throwable instanceof UnknownHostException) {
                        return Observable.error(throwable);
                    }
                    if (retryCount[0] > 0) {
                        retryCount[0]--;
                        return Observable.timer(2000, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error((Throwable) throwable);
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieBean list) {
                        if (list != null) {
                            callback.onSuccess(list.getSubjects());
                        }
                    }
                });
    }

    @Override
    public void getMovieDetail(String id, DetailCallback callback) {
        final int[] retryCount = {5};
        Observable<MovieDetailBean> observable = RetrofitFactory.getDouBanInstance().requestMovieDetail(id);
        observable
                .retryWhen(observable1 -> observable1.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                    if (throwable instanceof UnknownHostException) {
                        return Observable.error(throwable);
                    }
                    if (retryCount[0] > 0) {
                        retryCount[0]--;
                        return Observable.timer(2000, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error((Throwable) throwable);
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieDetailBean movieDetailBean) {
                        callback.onSuccess(movieDetailBean);
                    }
                });

    }
}
