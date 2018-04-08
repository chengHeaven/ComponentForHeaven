package com.github.chengheaven.movie.presenter;

import android.util.Log;
import android.view.View;

import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.data.DataRepository;
import com.github.chengheaven.movie.data.movie.MovieDataSource;
import com.github.chengheaven.movie.util.ResourceUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/27.
 */
public class MoviePresenter implements MovieContract.Presenter {

    private final MovieContract.View mView;
    private final DataRepository mDataRepository;

    @Inject
    MoviePresenter(MovieContract.View view, DataRepository dataRepository) {
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
    public void getMovieData(View view) {
        mDataRepository.getHotMovie(new MovieDataSource.Callback<MovieBean.SubjectsBean>() {
            @Override
            public void onSuccess(List<MovieBean.SubjectsBean> result) {
                Observable.just(result)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mView.update(list);
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
                            Log.d("MoviePresenter", msg);
                        });
            }
        });
    }
}
