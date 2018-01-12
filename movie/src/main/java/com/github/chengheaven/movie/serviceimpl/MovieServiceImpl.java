package com.github.chengheaven.movie.serviceimpl;

import android.support.v4.app.Fragment;

import com.github.chengheaven.componentservice.service.movie.MovieService;
import com.github.chengheaven.movie.view.MovieFragment;

/**
 * @author Heaven_Cheng Created on 2018/1/12.
 */
public class MovieServiceImpl implements MovieService{

    @Override
    public Fragment getMovieFragment() {
        return MovieFragment.newInstance();
    }
}
