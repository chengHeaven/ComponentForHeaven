package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public interface WelfareContract {

    interface View extends BaseView {
        /**
         * Update welfare page
         *
         * @param urls welfare image urls
         */
        void updateList(List<String> urls);
    }

    interface Presenter extends BasePresenter {

        /**
         * request welfare image urls
         *
         * @param view layout or widget
         * @param id   welfare
         * @param page count of requests for data
         * @param per  number of request data
         */
        void getWelfare(android.view.View view, String id, int page, int per);
    }
}
