package com.github.chengheaven.news.di.component;

import com.github.chengheaven.componentservice.view.FragmentScoped;
import com.github.chengheaven.news.di.module.NewsModule;
import com.github.chengheaven.news.view.news.NewsActivity;

import dagger.Component;

@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = NewsModule.class)
public interface NewsComponent {
    void inject(NewsActivity activity);
}
