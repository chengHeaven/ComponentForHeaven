package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public interface TechnologyContract {

    interface View extends BaseView {

        void setCurrentItem(int position);
    }

    interface Presenter extends BasePresenter {

        void clear();
    }
}
