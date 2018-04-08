package com.github.chengheaven.movie.di.component;

import com.github.chengheaven.componentservice.view.FragmentScoped;
import com.github.chengheaven.movie.di.module.MovieModule;
import com.github.chengheaven.movie.view.MovieActivity;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MovieModule.class)
public interface MovieComponent {

    void inject(MovieActivity activity);
}
