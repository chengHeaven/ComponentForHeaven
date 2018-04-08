package com.github.chengheaven.book.data.book;

import android.content.Context;

import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.data.RetrofitFactory;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookDataSourceImpl implements BookDataSource {

    private Context mContext;

    public BookDataSourceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void requestBookData(String tag, int start, int count, Callback callback) {

        final int[] retryCount = {5};

        Observable<BookData> observable = RetrofitFactory.getDouBanInstance().getBook(tag, start, count);
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
                .subscribe(new Subscriber<BookData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BookData data) {
                        if (data.getBooks() != null) {
                            callback.onSuccess(data);
                        }
                    }
                });
    }

    @Override
    public void requestBookDetail(String id, DetailCallback callback) {

        final int[] retryCount = {5};

        Observable<BookData.BookBean> observable = RetrofitFactory.getDouBanInstance().getBookDetail(id);
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
                .subscribe(new Subscriber<BookData.BookBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BookData.BookBean bookBean) {
                        if (bookBean != null) {
                            callback.onSuccess(bookBean);
                        }
                    }
                });
    }
}
