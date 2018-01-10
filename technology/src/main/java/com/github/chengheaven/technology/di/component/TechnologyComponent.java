package com.github.chengheaven.technology.di.component;

import com.github.chengheaven.technology.di.module.TechnologyModule;
import com.github.chengheaven.technology.view.technology.TechnologyActivity;
import com.github.chengheaven.componentservice.view.FragmentScoped;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@FragmentScoped
@dagger.Component(dependencies = DataRepositoryComponent.class, modules = TechnologyModule.class)
public interface TechnologyComponent {

    void inject(TechnologyActivity activity);
}
