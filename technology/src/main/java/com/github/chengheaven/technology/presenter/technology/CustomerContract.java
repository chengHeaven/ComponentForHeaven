package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;
import com.github.chengheaven.technology.bean.GankData;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public interface CustomerContract {

    interface View extends BaseView {

        /**
         * Load more data
         *
         * @param list List<GankData.ResultsBean>
         */
        void loadMoreData(List<GankData.ResultsBean> list);

        /**
         * Update a type data
         *
         * @param list List<GankData.ResultsBean>
         */
        void loadAnotherTypeData(List<GankData.ResultsBean> list);

        /**
         * Set type
         *
         * @param type Android / iOS / 全部 / 前端 / 瞎推荐 / 休息视频 / 拓展资源 / App
         */
        void setType(String type);

        /**
         * every fragment intent to this
         */
        void isIntent();
    }

    interface Presenter extends BasePresenter {

        /**
         * clear local data
         */
        void clear();

        /**
         * Request data
         *
         * @param view layout or widget
         * @param type request data or refresh data
         * @param id   Android / iOS / all / 前端 / 瞎推荐 / 休息视频 / 拓展资源 / App
         * @param page count of requests for data
         * @param per  number of request data
         */
        void getCustomizationData(android.view.View view, String type, String id, int page, int per);
    }
}
