package com.github.chengheaven.movie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.app.MovieApp;
import com.github.chengheaven.movie.di.component.DaggerMovieBaseComponent;
import com.github.chengheaven.movie.di.module.MovieModule;
import com.github.chengheaven.movie.presenter.MoviePresenter;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class MovieBaseFragment extends Fragment {

    @Inject
    MoviePresenter mPresenter;

    public static MovieBaseFragment newInstance() {
        return new MovieBaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_base_frag, container, false);

        MovieFragment movieFragment = (MovieFragment) getChildFragmentManager().findFragmentById(R.id.movie_base_frame);

        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.movie_base_frame, movieFragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerMovieBaseComponent.builder()
                .dataRepositoryComponent(MovieApp.getInstance().getDataRepositoryComponent())
                .movieModule(new MovieModule(movieFragment))
                .build()
                .inject(this);

        return view;
    }
}
