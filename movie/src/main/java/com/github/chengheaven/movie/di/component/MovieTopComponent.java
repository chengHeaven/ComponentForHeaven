package com.github.chengheaven.movie.di.component;

import com.github.chengheaven.componentservice.view.FragmentScoped;
import com.github.chengheaven.movie.di.module.MovieTopModule;
import com.github.chengheaven.movie.view.top.MovieTopActivity;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MovieTopModule.class)
public interface MovieTopComponent {

    void inject(MovieTopActivity activity);
}
