package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.technology.bean.HomeBean;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public interface EveryContract {

    interface View extends BaseView {

        /**
         * update banner view
         *
         * @param urls url
         */
        void updateBannerUrl(List<String> urls);

        /**
         * update home page view
         *
         * @param lists ArrayList<T>
         */
        void updateRecyclerAdapter(List<List<HomeBean>> lists);

        /**
         * cancel animation
         */
        void hideAnimation();

        void stopAnimation();

        /**
         * Show animation
         */
        void showAnimation();

        /**
         * request home page data again
         */
        void getAgainRecycler();
    }

    interface Presenter extends BasePresenter {

        /**
         * request banner data
         */
        void getBannerUrl();

        /**
         * request home page data
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void getRecycler(String year, String month, String day);

        /**
         * get home page data from local
         */
        void getRecyclerAndBannerFromLocal();

        /**
         * request 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all | 瞎推荐 data
         *
         * @param view activity / fragment / widget
         * @param id   福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all | 瞎推荐
         * @param page 第几页
         * @param pre  每页多少条数据
         */
        void getGankData(android.view.View view, String id, int page, int pre);
    }
}
