package com.github.chengheaven.book.presenter.book;


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
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LiteraturePresenter implements LiteratureContract.Presenter {

    private final LiteratureContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    LiteraturePresenter(LiteratureContract.View view, DataRepository dataRepository) {
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
    public void getLiteratureList(View view, String type, String tag, int start, int count) {
        mDataRepository.requestBookData(tag, start, count, new BookDataSource.Callback() {
            @Override
            public void onSuccess(BookData data) {
                Observable.just(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            if (type == null) {
                                mView.updateLiteratureView(result.getBooks());
                            } else {
                                mView.refreshLiteratureView(result.getBooks());
                            }
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
                            Log.d("LiteraturePresenter", msg);
                        });
            }
        });
    }
}


