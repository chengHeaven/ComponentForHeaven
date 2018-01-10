package com.github.chengheaven.technology.di.module;

import com.github.chengheaven.technology.presenter.welfareimage.WelfareImageContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/8.
 */
@Module
public class WelfareImageModule {

    private final WelfareImageContract.View mView;

    public WelfareImageModule(WelfareImageContract.View view) {
        this.mView = view;
    }

    @Provides
    WelfareImageContract.View provideWelfareImageContractView() {
        return mView;
    }
}
