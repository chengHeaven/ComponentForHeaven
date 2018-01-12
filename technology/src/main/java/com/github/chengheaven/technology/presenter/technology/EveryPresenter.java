package com.github.chengheaven.technology.presenter.technology;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.HomeBean;
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
public class EveryPresenter implements EveryContract.Presenter {

    private final EveryContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    EveryPresenter(EveryContract.View view, DataRepository dataRepository) {
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
    public void getBannerUrl() {
        mDataRepository.getBanner(new TechnologyDataSource.EveryCallback<String>() {

            @Override
            public void onSuccess(List<String> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mView::updateBannerUrl);
            }

            @Override
            public void onFailed(String msg) {
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(i -> {
                            mView.stopAnimation();
                            if (msg.contains(ResourceUtil.getString(R.string.technology_network_connectionless))) {
                                mView.toastMessage(ResourceUtil.getString(R.string.technology_network_connectionless_chinese));
                            } else {
                                mView.toastMessage(msg);
                            }
                        });
            }
        });
    }

    @Override
    public void getRecycler(String year, String month, String day) {
        mDataRepository.getRecycler(year, month, day, new TechnologyDataSource.EveryContentCallback<HomeBean>() {
            @Override
            public void onSuccess(List<List<HomeBean>> results) {
                Observable.just(results)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object -> {
                            mView.updateRecyclerAdapter(object);
                            Observable.timer(3000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(v -> mView.hideAnimation());
                        });
            }

            @Override
            public void onFailed(String msg) {
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(i -> {
                            if (ResourceUtil.getString(R.string.technology_result_empty).equals(msg)) {
                                mView.getAgainRecycler();
                            } else if (msg.contains(ResourceUtil.getString(R.string.technology_network_connectionless))) {
                                mView.stopAnimation();
                                mView.toastMessage(ResourceUtil.getString(R.string.technology_network_connectionless_chinese));
                            } else {
                                mView.stopAnimation();
                                mView.toastMessage(msg);
                            }
                        });
            }
        });
    }

    @Override
    public void getRecyclerAndBannerFromLocal() {
        List<String> urls = mDataRepository.getImageUrlsLocal();
        List<List<HomeBean>> list = mDataRepository.getLocalHomeData();
        if (urls != null && urls.size() != 0 && list != null && list.size() != 0) {
            mView.updateBannerUrl(urls);
            mView.updateRecyclerAdapter(list);
            mView.hideAnimation();
        } else {
            mView.showAnimation();
            mView.stopAnimation();
        }
    }

    @Override
    public void getGankData(View view, String id, int page, int pre) {
        mDataRepository.getGankData(id, page, pre, new TechnologyDataSource.EveryCallback() {
            @Override
            public void onSuccess(List results) {
                Log.d("EveryPresenter", "results:" + results);
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
                            mView.stopAnimation();
                            Log.d("EveryPresenter", msg);
                        });
            }
        });
    }
}
