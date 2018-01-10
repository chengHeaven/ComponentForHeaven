package com.github.chengheaven.technology.di.module;

import com.github.chengheaven.technology.presenter.technology.AndroidContract;
import com.github.chengheaven.technology.presenter.technology.CustomerContract;
import com.github.chengheaven.technology.presenter.technology.EveryContract;
import com.github.chengheaven.technology.presenter.technology.WelfareContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@Module
public class GankModule {

    private final AndroidContract.View mAndroidView;
    private final CustomerContract.View mCustomerView;
    private final EveryContract.View mEveryView;
    private final WelfareContract.View mWelfareView;

    public GankModule(
            EveryContract.View everyView,
            WelfareContract.View welfareView,
            CustomerContract.View customerView,
            AndroidContract.View androidView) {
        this.mEveryView = everyView;
        this.mWelfareView = welfareView;
        this.mCustomerView = customerView;
        this.mAndroidView = androidView;
    }

    @Provides
    AndroidContract.View provideAndroidView() {
        return mAndroidView;
    }

    @Provides
    CustomerContract.View provideCustomerView() {
        return mCustomerView;
    }

    @Provides
    EveryContract.View provideEveryView() {
        return mEveryView;
    }

    @Provides
    WelfareContract.View provideWelfareView() {
        return mWelfareView;
    }
}
