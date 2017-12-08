package com.github.chengheaven.news.runalone.view.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.chengheaven.news.R;
import com.github.chengheaven.news.runalone.application.NewsApplication;
import com.github.chengheaven.news.runalone.di.component.DaggerNewsTestComponent;
import com.github.chengheaven.news.runalone.di.module.NewsModule;
import com.github.chengheaven.news.runalone.presenter.news.NewsPresenter;

import javax.inject.Inject;

public class NewsTestActivity extends AppCompatActivity {

    @Inject
    NewsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_test);

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.tab_content);
        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.tab_content, newsFragment);
            transaction.commit();
        }

        DaggerNewsTestComponent.builder()
                .testDataRepositoryComponent(((NewsApplication) getApplicationContext()).getDataRepositoryComponent())
                .newsModule(new NewsModule(newsFragment))
                .build()
                .inject(this);
    }
}
