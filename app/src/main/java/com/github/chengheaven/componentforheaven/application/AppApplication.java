package com.github.chengheaven.componentforheaven.application;

import android.app.Application;

import com.github.chengheaven.componentlib.router.Router;

/**
 * @author Heaven・Cheng Created on 2017/9/22.
 */

public class AppApplication extends Application {

    private AppApplication sInstance;
//    private DataRepositoryComponent mDataRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //如果isRegisterCompoAuto为false，则需要通过反射加载组件
        Router.registerComponent(getApplicationContext(),
                "com.github.chengheaven.news.app.NewsApp");

//        mDataRepositoryComponent = DaggerDataRepositoryComponent
//                .builder()
//                .applicationModule(new ApplicationModule(getBaseContext()))
//                .build();
    }

//    public DataRepositoryComponent getDataRepositoryComponent() {
//        return mDataRepositoryComponent;
//    }

    public Application getInstance() {
        return sInstance;
    }
}
