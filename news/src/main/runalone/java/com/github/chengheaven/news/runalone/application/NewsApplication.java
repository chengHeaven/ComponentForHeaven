package com.github.chengheaven.news.runalone.application;

import android.app.Application;

import com.github.chengheaven.news.runalone.di.component.DaggerTestDataRepositoryComponent;
import com.github.chengheaven.news.runalone.di.component.TestDataRepositoryComponent;
import com.github.chengheaven.news.runalone.di.module.ApplicationModule;

/**
 * @author Heaven・Cheng Created on 2017/9/25.
 */

public class NewsApplication extends Application {

    private TestDataRepositoryComponent mDataRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mDataRepositoryComponent = DaggerTestDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public TestDataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }
}