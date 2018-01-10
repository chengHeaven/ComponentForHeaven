package com.github.chengheaven.technology.view.technology;

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

import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.app.TechnologyApp;
import com.github.chengheaven.technology.di.component.DaggerGankComponent;
import com.github.chengheaven.technology.di.module.GankModule;
import com.github.chengheaven.technology.presenter.technology.AndroidPresenter;
import com.github.chengheaven.technology.presenter.technology.CustomerPresenter;
import com.github.chengheaven.technology.presenter.technology.EveryPresenter;
import com.github.chengheaven.technology.presenter.technology.TechnologyContract;
import com.github.chengheaven.technology.presenter.technology.WelfarePresenter;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyFragment extends BaseFragment implements TechnologyContract.View {

    TabLayout mCodeTab;
    ViewPager mCodeViewpager;

    @Inject
    EveryPresenter mEveryPresenter;

    @Inject
    WelfarePresenter mWelfarePresenter;

    @Inject
    CustomerPresenter mCustomerPresenter;

    @Inject
    AndroidPresenter mAndroidPresenter;


    private TechnologyContract.Presenter mCodePresenter;

    public TechnologyFragment() {

    }

    public static TechnologyFragment newInstance() {
        return new TechnologyFragment();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mCodePresenter = (TechnologyContract.Presenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_frag, container, false);

        mCodeTab = view.findViewById(R.id.code_tab);
        mCodeViewpager = view.findViewById(R.id.code_viewpager);

        List<Fragment> fragments = new ArrayList<>();
        EveryFragment everyFragment = EveryFragment.newInstance();
        WelfareFragment welfareFragment = WelfareFragment.newInstance();
        CustomerFragment customerFragment = CustomerFragment.newInstance();
        AndroidFragment androidFragment = AndroidFragment.newInstance();

        fragments.add(everyFragment);
        fragments.add(welfareFragment);
        fragments.add(customerFragment);
        fragments.add(androidFragment);

        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("福利");
        titles.add("定制");
        titles.add("安卓");

        TabPagerAdapter adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        mCodeViewpager.setAdapter(adapter);
        mCodeViewpager.setOffscreenPageLimit(2);
        mCodeTab.setupWithViewPager(mCodeViewpager);
        mCodeTab.setTabMode(TabLayout.MODE_FIXED);

        DaggerGankComponent.builder()
                .dataRepositoryComponent(TechnologyApp.getInstance().getDataRepositoryComponent())
                .gankModule(new GankModule(
                        everyFragment,
                        welfareFragment,
                        customerFragment,
                        androidFragment
                ))
                .build()
                .inject(this);

        return view;
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        TabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.mFragments = fragments;
            this.mTitles = titles;
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
