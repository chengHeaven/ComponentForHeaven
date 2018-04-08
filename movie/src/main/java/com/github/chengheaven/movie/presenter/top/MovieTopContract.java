package com.github.chengheaven.movie.presenter.top;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;
import com.github.chengheaven.movie.bean.MovieBean;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public interface MovieTopContract {

    interface View extends BaseView {

        /**
         * Update movie top data
         *
         * @param list List<MovieBean>
         */
        void update(List<MovieBean.SubjectsBean> list);

        /**
         * Refresh movie top data
         *
         * @param list List<MovieBean>
         */
        void refresh(List<MovieBean.SubjectsBean> list);
    }

    interface Presenter extends BasePresenter {

        /**
         * Request movie top data
         *
         * @param view  widget or layout
         * @param type  type == null is update data, not null is refresh data
         * @param start start of requests for data
         * @param count count of requests for data
         */
        void getMovieTopData(android.view.View view, String type, int start, int count);
    }
}
