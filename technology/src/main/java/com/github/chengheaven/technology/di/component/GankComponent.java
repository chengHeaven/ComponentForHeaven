package com.github.chengheaven.technology.di.component;

import com.github.chengheaven.technology.di.module.GankModule;
import com.github.chengheaven.technology.view.technology.TechnologyFragment;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = GankModule.class)
public interface GankComponent {

    void inject(TechnologyFragment fragment);
}
