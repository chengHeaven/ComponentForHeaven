package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public interface AndroidContract {

    interface View extends BaseView {

        /**
         * Update data
         *
         * @param list List<GankData.ResultsBean>
         */
        void updateList(List<GankData.ResultsBean> list);

        /**
         * Refresh data
         *
         * @param list List<GankData.ResultsBean>
         */
        void refreshList(List<GankData.ResultsBean> list);

    }

    interface Presenter extends BasePresenter {

        /**
         * Request android data
         *
         * @param view layout or widget
         * @param type request or refresh data
         * @param id   android
         * @param page count of requests for data
         * @param per  number of request data
         */
        void getAndroidData(android.view.View view, String type, String id, int page, int per);
    }
}
