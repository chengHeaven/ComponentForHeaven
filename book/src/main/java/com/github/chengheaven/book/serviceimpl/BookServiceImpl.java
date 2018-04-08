package com.github.chengheaven.book.serviceimpl;

import android.support.v4.app.Fragment;

import com.github.chengheaven.book.view.BookBaseFragment;
import com.github.chengheaven.book.view.BookViewPagerFragment;
import com.github.chengheaven.componentservice.service.book.BookService;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookServiceImpl implements BookService {

    @Override
    public Fragment getBookBaseFragment() {
        return BookBaseFragment.newInstance();
    }

    @Override
    public Fragment getBookViewPagerFragment() {
        return BookViewPagerFragment.newInstance();
    }
}
