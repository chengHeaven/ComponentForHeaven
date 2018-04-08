package com.github.chengheaven.book.presenter.detail;

import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public interface BookDetailContract {

    interface View extends BaseView {

        void updateTitleView(BookData.BookBean result);

        void updateView(BookData.BookBean result);
    }

    interface Presenter extends BasePresenter {

        void requestBookDetailData(android.view.View view);
    }
}
