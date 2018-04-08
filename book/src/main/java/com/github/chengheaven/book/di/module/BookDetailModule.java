package com.github.chengheaven.book.di.module;

import com.github.chengheaven.book.presenter.detail.BookDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */

@Module
public class BookDetailModule {

    private final BookDetailContract.View mView;

    public BookDetailModule(BookDetailContract.View view) {
        this.mView = view;
    }

    @Provides
    BookDetailContract.View provideBookDetailContractView() {
        return mView;
    }
}
