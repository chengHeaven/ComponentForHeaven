package com.github.chengheaven.book.di.module;

import com.github.chengheaven.book.presenter.book.CultureContract;
import com.github.chengheaven.book.presenter.book.LifeContract;
import com.github.chengheaven.book.presenter.book.LiteratureContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@Module
public class BookViewPagerModule {

    private final LiteratureContract.View mLiteratureFragment;
    private final CultureContract.View mCultureFragment;
    private final LifeContract.View mLifeFragment;

    public BookViewPagerModule(LiteratureContract.View literatureFragment,
                               CultureContract.View cultureFragment,
                               LifeContract.View lifeFragment) {
        this.mLiteratureFragment = literatureFragment;
        this.mCultureFragment = cultureFragment;
        this.mLifeFragment = lifeFragment;
    }

    @Provides
    LiteratureContract.View provideLiteratureContractView() {
        return mLiteratureFragment;
    }

    @Provides
    CultureContract.View provideCultureContractView() {
        return mCultureFragment;
    }

    @Provides
    LifeContract.View provideLifeContractView() {
        return mLifeFragment;
    }
}
