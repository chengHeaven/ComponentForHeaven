package com.github.chengheaven.book.di.module;

import com.github.chengheaven.book.presenter.BookContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */

@Module
public class BookModule {

    private final BookContract.View mView;

    public BookModule(BookContract.View view) {
        mView = view;
    }

    @Provides
    BookContract.View provideBookContractView() {
        return mView;
    }
}
