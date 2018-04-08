package com.github.chengheaven.movie.di.component;

import com.github.chengheaven.componentservice.view.FragmentScoped;
import com.github.chengheaven.movie.di.module.MovieDetailModule;
import com.github.chengheaven.movie.view.detail.MovieDetailActivity;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MovieDetailModule.class)
public interface MovieDetailComponent {

    void inject(MovieDetailActivity activity);
}
