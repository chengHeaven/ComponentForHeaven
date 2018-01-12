package com.github.chengheaven.technology.presenter.technology;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.componentservice.utils.RxBus;
import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.bean.rx.RxCustomer;
import com.github.chengheaven.technology.data.DataRepository;
import com.github.chengheaven.technology.data.technology.TechnologyDataSource;
import com.github.chengheaven.technology.util.ResourceUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class CustomerPresenter implements CustomerContract.Presenter {

    private final CustomerContract.View mView;
    private final DataRepository mDataRepository;

    private Disposable mDisposable;

    @Inject
    CustomerPresenter(CustomerContract.View view, DataRepository dataRepository) {
        this.mView = view;
        this.mDataRepository = dataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        // noinspection unchecked
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        registerShowTypeContent();
    }

    private void registerShowTypeContent() {
        RxBus.getDefault().toObservable(RxCustomer.class)
                .subscribe(new Observer<RxCustomer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(RxCustomer value) {
                        mView.setType(value.getType());
                        mView.isIntent();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void clear() {
        mDataRepository.clear();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }

    @Override
    public void getCustomizationData(View view, String type, String id, int page, int per) {
        mDataRepository.getGankData(id, page, per, new TechnologyDataSource.EveryCallback<GankData.ResultsBean>() {
            @Override
            public void onSuccess(List<GankData.ResultsBean> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            if (type == null) {
                                mDataRepository.setCustomizationToLocal(data, false);
                                mView.loadAnotherTypeData(data);
                            } else {
                                mDataRepository.setCustomizationToLocal(data, true);
                                mView.loadMoreData(data);
                            }
                            Observable.timer(3000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(v -> mView.hideLoading());
                        });
            }

            @Override
            public void onFailed(String msg) {
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(i -> {
                            if (msg.contains(ResourceUtil.getString(R.string.technology_network_connectionless))) {
                                Utils.showSnackBar(view, ResourceUtil.getString(R.string.technology_network_connectionless_chinese));
                            }
                            mView.hideLoading();
                            if (page == 1) {
                                mView.showError();
                            }
                            mView.loadMoreData(null);
                            mView.toastMessage(msg);
                            Log.d("CustomerPresenter", msg);
                        });
            }
        });
    }
}
