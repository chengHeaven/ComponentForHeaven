package com.github.chengheaven.book.di.module;

import com.github.chengheaven.book.view.BookViewPagerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@Module
public class BookBaseModule {

    private final BookViewPagerFragment mView;

    public BookBaseModule(BookViewPagerFragment view) {
        this.mView = view;
    }

    @Provides
    BookViewPagerFragment provideBookViewPagerFragment() {
        return mView;
    }
}
