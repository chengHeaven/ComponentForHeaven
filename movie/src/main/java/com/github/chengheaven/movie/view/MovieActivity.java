package com.github.chengheaven.movie.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.app.MovieApp;
import com.github.chengheaven.movie.di.component.DaggerMovieComponent;
import com.github.chengheaven.movie.di.module.MovieModule;
import com.github.chengheaven.movie.presenter.MoviePresenter;

import javax.inject.Inject;

public class MovieActivity extends AppCompatActivity {

    @Inject
    MoviePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_act);

        MovieFragment movieFragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.movie_content_frame);
        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.movie_content_frame, movieFragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerMovieComponent.builder()
                .dataRepositoryComponent(MovieApp.getInstance().getDataRepositoryComponent())
                .movieModule(new MovieModule(movieFragment))
                .build()
                .inject(this);
    }
}
