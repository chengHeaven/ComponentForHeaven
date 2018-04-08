package com.github.chengheaven.book.di.component;

import com.github.chengheaven.book.di.module.BookDetailModule;
import com.github.chengheaven.book.view.detail.BookDetailActivity;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookDetailModule.class)
public interface BookDetailComponent {

    void inject(BookDetailActivity activity);
}
