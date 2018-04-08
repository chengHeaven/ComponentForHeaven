package com.github.chengheaven.book.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.app.BookApp;
import com.github.chengheaven.book.di.component.DaggerBookBaseComponent;
import com.github.chengheaven.book.di.module.BookBaseModule;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookBaseFragment extends Fragment {

//    @Inject
//    BookPresenter mPresenter;

    public static BookBaseFragment newInstance() {
        return new BookBaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_act, container, false);

        BookViewPagerFragment fragment = (BookViewPagerFragment) getChildFragmentManager()
                .findFragmentById(R.id.book_content_frame);

        if (fragment == null) {
            fragment = BookViewPagerFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.book_content_frame, fragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerBookBaseComponent.builder()
                .dataRepositoryComponent(BookApp.getInstance().getDataRepositoryComponent())
                .bookBaseModule(new BookBaseModule(fragment))
                .build()
                .inject(this);


        return view;
    }
}
