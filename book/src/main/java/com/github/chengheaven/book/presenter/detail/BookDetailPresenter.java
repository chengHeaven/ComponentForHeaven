package com.github.chengheaven.book.presenter.detail;

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
public class BookDetailPresenter implements BookDetailContract.Presenter {

    private final BookDetailContract.View mView;
    private final DataRepository mDataRepository;
    private BookData.BookBean mData;

    @Inject
    BookDetailPresenter(BookDetailContract.View view, DataRepository dataRepository) {
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
        mView.updateTitleView(getData());
    }

    public BookData.BookBean getData() {
        return mData;
    }

    public void setData(BookData.BookBean data) {
        this.mData = data;
    }

    @Override
    public void requestBookDetailData(View view) {
        mDataRepository.requestBookDetail(getData().getId(), new BookDataSource.DetailCallback() {
            @Override
            public void onSuccess(BookData.BookBean data) {
                Observable.just(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            mView.updateView(result);
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
                            Log.d("BookDetailPresenter", msg);
                        });
            }
        });
    }
}
