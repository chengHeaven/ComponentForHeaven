package com.github.chengheaven.movie.presenter;

import com.github.chengheaven.movie.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/12.
 */
public class MoviePresenter implements MovieContract.Presenter{

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

    }

    @Override
    public void start() {

    }

}
