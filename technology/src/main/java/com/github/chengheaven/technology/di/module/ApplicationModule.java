package com.github.chengheaven.technology.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
