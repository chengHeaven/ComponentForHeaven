package com.github.chengheaven.book.data;

import com.github.chengheaven.book.data.book.BookDataSource;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class DataRepository implements BookDataSource {

    private final BookDataSource mBookDataSource;

    @Inject
    public DataRepository(BookDataSource bookDataSource) {
        mBookDataSource = bookDataSource;
    }

    @Override
    public void requestBookData(String tag, int start, int count, Callback callback) {
        mBookDataSource.requestBookData(tag, start, count, callback);
    }

    @Override
    public void requestBookDetail(String id, DetailCallback callback) {
        mBookDataSource.requestBookDetail(id, callback);
    }
}
