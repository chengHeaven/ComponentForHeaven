package com.github.chengheaven.technology.di.component;

import com.github.chengheaven.technology.di.module.WelfareImageModule;
import com.github.chengheaven.technology.view.welfareimage.WelfareImageActivity;
import com.github.chengheaven.componentservice.view.FragmentScoped;

import dagger.Component;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = WelfareImageModule.class)
public interface WelfareImageComponent {

    void inject(WelfareImageActivity activity);
}
