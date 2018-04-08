package com.github.chengheaven.book.di.component;

import com.github.chengheaven.book.di.module.BookBaseModule;
import com.github.chengheaven.book.view.BookBaseFragment;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = BookBaseModule.class)
public interface BookBaseComponent {

    void inject(BookBaseFragment fragment);
}
