package com.github.chengheaven.movie.presenter.detail;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.bean.MovieDetailBean;
import com.github.chengheaven.movie.data.DataRepository;
import com.github.chengheaven.movie.data.movie.MovieDataSource;
import com.github.chengheaven.movie.util.ResourceUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View mView;
    private final DataRepository mDataRepository;
    private MovieBean.SubjectsBean mData;

    @Inject
    MovieDetailPresenter(MovieDetailContract.View view, DataRepository dataRepository) {
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

    @Override
    public void getMovieDetailData(View view) {
        mDataRepository.getMovieDetail(getData().getId(), new MovieDataSource.DetailCallback() {
            @Override
            public void onSuccess(MovieDetailBean bean) {
                Observable.just(bean)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            mView.update(result);
                            Observable.timer(3000, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(v -> mView.hideLoading());
                        });
            }

            @Override
            public void onFailure(String msg) {
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(i -> {
                            if (msg.contains(ResourceUtil.getString(R.string.movie_network_connectionless))) {
                                Utils.showSnackBar(view, ResourceUtil.getString(R.string.movie_network_connectionless_chinese));
                            }
                            mView.hideLoading();
                            mView.toastMessage(msg);
                            Log.d("MovieDetailPresenter", msg);
                        });
            }
        });
    }

    public MovieBean.SubjectsBean getData() {
        return mData;
    }

    public void setData(MovieBean.SubjectsBean data) {
        this.mData = data;
    }
}
