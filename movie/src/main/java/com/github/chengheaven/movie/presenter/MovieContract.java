package com.github.chengheaven.movie.presenter;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;
import com.github.chengheaven.movie.bean.MovieBean;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/27.
 */
public interface MovieContract {

    interface View extends BaseView {

        /**
         * update movie page
         *
         * @param list hot movie
         */
        void update(List<MovieBean.SubjectsBean> list);
    }

    interface Presenter extends BasePresenter {

        /**
         * request hot movie data
         *
         * @param view layout or widget
         */
        void getMovieData(android.view.View view);
    }
}
