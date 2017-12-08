package com.github.chengheaven.news.runalone.di.component;

import com.github.chengheaven.componentservice.view.FragmentScoped;
import com.github.chengheaven.news.runalone.di.module.NewsModule;
import com.github.chengheaven.news.runalone.view.news.NewsTestActivity;

import dagger.Component;

@FragmentScoped
@Component(dependencies = TestDataRepositoryComponent.class, modules = NewsModule.class)
public interface NewsTestComponent {
    void inject(NewsTestActivity activity);
}
