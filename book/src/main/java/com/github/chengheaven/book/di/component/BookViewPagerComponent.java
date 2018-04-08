package com.github.chengheaven.book.di.component;

import com.github.chengheaven.book.di.module.BookViewPagerModule;
import com.github.chengheaven.book.view.BookViewPagerFragment;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookViewPagerModule.class)
public interface BookViewPagerComponent {

    void inject(BookViewPagerFragment fragment);
}
