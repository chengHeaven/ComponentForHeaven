package com.github.chengheaven.book.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.presenter.BookContract;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookFragment extends BaseFragment implements BookContract.View {

    TabLayout mTab;
    ViewPager mViewpager;

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);

        mTab = view.findViewById(R.id.tab_gank);
        mViewpager = view.findViewById(R.id.viewpager_gank);

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Override
    public void update(BookData data) {

    }
}
