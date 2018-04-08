package com.github.chengheaven.book.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.app.BookApp;
import com.github.chengheaven.book.di.component.DaggerBookViewPagerComponent;
import com.github.chengheaven.book.di.module.BookViewPagerModule;
import com.github.chengheaven.book.presenter.book.CulturePresenter;
import com.github.chengheaven.book.presenter.book.LifePresenter;
import com.github.chengheaven.book.presenter.book.LiteraturePresenter;
import com.github.chengheaven.book.view.book.CultureFragment;
import com.github.chengheaven.book.view.book.LifeFragment;
import com.github.chengheaven.book.view.book.LiteratureFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookViewPagerFragment extends Fragment {

    TabLayout mTab;
    ViewPager mViewpager;

    @Inject
    LiteraturePresenter mLiteraturePresenter;
    @Inject
    CulturePresenter mCulturePresenter;
    @Inject
    LifePresenter mLifePresenter;

    public static BookViewPagerFragment newInstance() {
        return new BookViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_viewpager_fragment, container, false);

        mTab = view.findViewById(R.id.tab_gank);
        mViewpager = view.findViewById(R.id.viewpager_gank);

        List<Fragment> fragments = new ArrayList<>();
        LiteratureFragment literatureFragment = LiteratureFragment.newInstance("文学");
        CultureFragment cultureFragment = CultureFragment.newInstance();
        LifeFragment lifeFragment = LifeFragment.newInstance("生活");

        fragments.add(literatureFragment);
        fragments.add(cultureFragment);
        fragments.add(lifeFragment);

        List<String> titles = new ArrayList<>();
        titles.add("文学");
        titles.add("文化");
        titles.add("生活");

        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mViewpager);
        mTab.setTabMode(TabLayout.MODE_FIXED);


        DaggerBookViewPagerComponent.builder()
                .dataRepositoryComponent(BookApp.getInstance().getDataRepositoryComponent())
                .bookViewPagerModule(new BookViewPagerModule(
                        literatureFragment,
                        cultureFragment,
                        lifeFragment
                ))
                .build()
                .inject(this);

        return view;
    }

    class TabAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        TabAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
