package com.github.chengheaven.movie.view.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.github.chengheaven.componentservice.view.BaseActivity;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.app.MovieApp;
import com.github.chengheaven.movie.di.component.DaggerMovieTopComponent;
import com.github.chengheaven.movie.di.module.MovieTopModule;
import com.github.chengheaven.movie.presenter.top.MovieTopPresenter;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieTopActivity extends BaseActivity {

    Toolbar mToolbar;

    @Inject
    MovieTopPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("豆瓣电影Top250");

        MovieTopFragment movieTopFragment = (MovieTopFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_top_content_frame);

        if (movieTopFragment == null) {
            movieTopFragment = MovieTopFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.movie_top_content_frame, movieTopFragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerMovieTopComponent.builder()
                .dataRepositoryComponent(MovieApp.getInstance().getDataRepositoryComponent())
                .movieTopModule(new MovieTopModule(movieTopFragment))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.movie_top_act;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }
}
