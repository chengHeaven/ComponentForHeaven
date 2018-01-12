package com.github.chengheaven.movie.view;

import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.movie.presenter.MovieContract;

/**
 * @author Heaven_Cheng Created on 2018/1/12.
 */
public class MovieFragment extends BaseFragment implements MovieContract.View {



    public MovieFragment() {

    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
