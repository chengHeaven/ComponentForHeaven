package com.github.chengheaven.technology.presenter.technology;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.data.DataRepository;
import com.github.chengheaven.technology.data.technology.TechnologyDataSource;
import com.github.chengheaven.technology.util.ResourceUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class AndroidPresenter implements AndroidContract.Presenter {

    private final AndroidContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    AndroidPresenter(AndroidContract.View view, DataRepository dataRepository) {
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
    public void getAndroidData(View view, String type, String id, int page, int per) {
        mDataRepository.getGankData(id, page, per, new TechnologyDataSource.EveryCallback<GankData.ResultsBean>() {
            @Override
            public void onSuccess(List<GankData.ResultsBean> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            if (type == null) {
                                mDataRepository.setAndroidToLocal(data, false);
                                mView.refreshList(data);
                            } else {
                                mDataRepository.setAndroidToLocal(data, true);
                                mView.updateList(data);
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
                            mView.updateList(null);
                            mView.toastMessage(msg);
                            Log.d("AndroidPresenter", msg);
                        });
            }
        });
    }

    @Override
    public List<GankData.ResultsBean> getAndroidDataFromLocal() {
        return mDataRepository.getAndroidFromLocal();
    }
}
