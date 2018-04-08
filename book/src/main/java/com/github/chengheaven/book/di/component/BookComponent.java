package com.github.chengheaven.book.di.component;

import com.github.chengheaven.book.di.module.BookModule;
import com.github.chengheaven.book.view.BookActivity;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookModule.class)
public interface BookComponent {

    void inject(BookActivity activity);
}
