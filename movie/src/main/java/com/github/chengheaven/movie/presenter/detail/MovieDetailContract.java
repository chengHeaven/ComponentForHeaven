package com.github.chengheaven.movie.presenter.detail;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.bean.MovieDetailBean;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public interface MovieDetailContract {

    interface View extends BaseView {

        void update(MovieDetailBean result);

        void updateTitleView(MovieBean.SubjectsBean data);

    }

    interface Presenter extends BasePresenter {

        /**
         * Request movie detail data
         *
         * @param view widget or layout
         */
        void getMovieDetailData(android.view.View view);
    }
}
