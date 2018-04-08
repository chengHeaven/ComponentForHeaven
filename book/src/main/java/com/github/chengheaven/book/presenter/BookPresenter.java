package com.github.chengheaven.book.presenter;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.data.DataRepository;
import com.github.chengheaven.book.data.book.BookDataSource;
import com.github.chengheaven.componentservice.utils.Utils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookPresenter implements BookContract.Presenter {

    private final BookContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    BookPresenter(BookContract.View view, DataRepository dataRepository) {
        this.mView = view;
        this.mDataRepository = dataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void requestBookData(View view, int start, int count) {
        mDataRepository.requestBookData(view.getContext().getString(R.string.book_book), start, count, new BookDataSource.Callback() {
            @Override
            public void onSuccess(BookData data) {
                Observable.just(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            mView.update(result);
                            Observable.timer(2000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(i -> mView.hideLoading());
                        });
            }

            @Override
            public void onFailure(String msg) {
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(i -> {
                            if (msg.contains(view.getContext().getString(R.string.book_network_connectionless))) {
                                Utils.showSnackBar(view, view.getContext().getString(R.string.book_network_connectionless_chinese));
                            }
                            mView.hideLoading();
                            mView.toastMessage(msg);
                            Log.d("BookPresenter", msg);
                        });
            }
        });
    }
}
