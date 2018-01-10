package com.github.chengheaven.technology.presenter.welfareimage;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2018/1/8.
 */
public interface WelfareImageContract {

    interface View extends BaseView {

        /**
         * Update viewpager data
         *
         * @param urls image urls
         */
        void updateViewPager(List<String> urls);

        /**
         * Set the currently selected page
         *
         * @param position currently selected page
         */
        void setCurrentItemViewPager(int position);

        /**
         * Set the viewpager page count
         *
         * @param count viewpager page count
         */
        void setPageCount(int count);

        /**
         * Update indicator
         *
         * @param position currently selected page
         */
        void updateIndicator(int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * Initialize viewpager data
         */
        void initViewPagerData();
    }
}
