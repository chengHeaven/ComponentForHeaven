package com.github.chengheaven.book.presenter;

import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public interface BookContract {

    interface View extends BaseView {

        void update(BookData data);
    }

    interface Presenter extends BasePresenter {

        void requestBookData(android.view.View view, int start, int count);
    }
}
