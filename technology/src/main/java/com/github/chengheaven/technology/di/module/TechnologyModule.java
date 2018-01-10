package com.github.chengheaven.technology.di.module;

import com.github.chengheaven.technology.presenter.technology.TechnologyContract;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@dagger.Module
public class TechnologyModule {
    private final TechnologyContract.View mView;

    public TechnologyModule(TechnologyContract.View mView) {
        this.mView = mView;
    }

    @dagger.Provides
    TechnologyContract.View provideCodeContractView() {
        return mView;
    }
}
