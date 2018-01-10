package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.technology.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyPresenter implements TechnologyContract.Presenter {

    private final TechnologyContract.View mCodeView;
    private final DataRepository mDataRepository;

    @Inject
    TechnologyPresenter(TechnologyContract.View mCodeView, DataRepository mDataRepository) {
        this.mCodeView = mCodeView;
        this.mDataRepository = mDataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mCodeView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
