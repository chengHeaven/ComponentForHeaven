package com.github.chengheaven.news.view.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.chengheaven.news.R;
import com.github.chengheaven.news.app.NewsApp;
import com.github.chengheaven.news.di.component.DaggerNewsComponent;
import com.github.chengheaven.news.di.module.NewsModule;
import com.github.chengheaven.news.presenter.news.NewsPresenter;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 2017/9/25.
 */

public class NewsActivity extends AppCompatActivity {

    @Inject
    NewsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_hot_top_activity);
//        Fragment fragment = new NewsFragment();
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.tab_content, fragment).commitAllowingStateLoss();

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.tab_content);

        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.tab_content, newsFragment);
            transaction.commitAllowingStateLoss();
        }


        DaggerNewsComponent.builder()
                .dataRepositoryComponent(NewsApp.getInstance().getDataRepositoryComponent())
                .newsModule(new NewsModule(newsFragment))
                .build()
                .inject(this);
    }
}
