package com.github.chengheaven.book.data.book;

import com.github.chengheaven.book.bean.BookData;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public interface BookDataSource {

    interface Callback {

        void onSuccess(BookData data);

        void onFailure(String msg);
    }

    interface DetailCallback {

        void onSuccess(BookData.BookBean data);

        void onFailure(String msg);
    }

    void requestBookData(String tag, int start, int count, Callback callback);

    void requestBookDetail(String id, DetailCallback callback);
}
