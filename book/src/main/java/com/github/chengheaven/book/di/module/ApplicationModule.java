package com.github.chengheaven.book.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
