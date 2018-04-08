package com.github.chengheaven.book.presenter.book;

import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.BaseView;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class LifeContract {

    public interface View extends BaseView {

        void refreshLifeView(List<BookData.BookBean> results);

        void updateLifeView(List<BookData.BookBean> results);
    }

    public interface Presenter extends BasePresenter {

        void getLifeList(android.view.View view, String type, String tag, int start, int count);
    }
}