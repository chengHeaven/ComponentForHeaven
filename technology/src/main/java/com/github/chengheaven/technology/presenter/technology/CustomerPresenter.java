package com.github.chengheaven.technology.presenter.technology;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.data.DataRepository;
import com.github.chengheaven.technology.data.technology.TechnologyDataSource;
import com.github.chengheaven.technology.util.ResourceUtil;
import com.github.chengheaven.componentservice.utils.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class CustomerPresenter implements CustomerContract.Presenter {

    private final CustomerContract.View mView;
    private final DataRepository mDataRepository;

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

    }

    @Override
    public void clear() {
        mDataRepository.clear();
    }

    @Override
    public void getCustomizationData(View view, String type, String id, int page, int per) {
        mDataRepository.getGankData(id, page, per, new TechnologyDataSource.EveryCallback<GankData.ResultsBean>() {
            @Override
            public void onSuccess(List<GankData.ResultsBean> results) {
                mView.hideLoading();
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            if (type == null) {
                                mView.loadAnotherTypeData(data);
                            } else {
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
                            if (msg.contains(ResourceUtil.getString(R.string.code_network_connectionless))) {
                                Utils.showSnackBar(view, ResourceUtil.getString(R.string.code_network_connectionless_chinese));
                            }
                            mView.hideLoading();
                            if (page==1) {
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
