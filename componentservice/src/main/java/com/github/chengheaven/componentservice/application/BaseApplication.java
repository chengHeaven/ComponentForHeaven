package com.github.chengheaven.componentservice.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }
}
