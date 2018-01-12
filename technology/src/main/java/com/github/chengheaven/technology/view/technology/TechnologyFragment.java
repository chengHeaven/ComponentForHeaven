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

import com.github.chengheaven.componentservice.utils.RxBus;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.app.TechnologyApp;
import com.github.chengheaven.technology.bean.rx.RxPosition;
import com.github.chengheaven.technology.di.component.DaggerGankComponent;
import com.github.chengheaven.technology.di.module.GankModule;
import com.github.chengheaven.technology.presenter.technology.AndroidPresenter;
import com.github.chengheaven.technology.presenter.technology.CustomerPresenter;
import com.github.chengheaven.technology.presenter.technology.EveryPresenter;
import com.github.chengheaven.technology.presenter.technology.TechnologyContract;
import com.github.chengheaven.technology.presenter.technology.WelfarePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyFragment extends BaseFragment implements TechnologyContract.View {

    TabLayout mTechnologyTab;
    ViewPager mTechnologyViewpager;

    @Inject
    EveryPresenter mEveryPresenter;

    @Inject
    WelfarePresenter mWelfarePresenter;

    @Inject
    CustomerPresenter mCustomerPresenter;

    @Inject
    AndroidPresenter mAndroidPresenter;

    private TechnologyContract.Presenter mPresenter;

    private Disposable mDisposable;

    public TechnologyFragment() {

    }

    public static TechnologyFragment newInstance() {
        return new TechnologyFragment();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (TechnologyContract.Presenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.technology_frag, container, false);

        mTechnologyTab = view.findViewById(R.id.technology_tab);
        mTechnologyViewpager = view.findViewById(R.id.technology_viewpager);

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
        mTechnologyViewpager.setAdapter(adapter);
        mTechnologyViewpager.setOffscreenPageLimit(2);
        mTechnologyTab.setupWithViewPager(mTechnologyViewpager);
        mTechnologyTab.setTabMode(TabLayout.MODE_FIXED);

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

        registerPosition();

        return view;
    }

    private void registerPosition() {
        RxBus.getDefault().toObservable(RxPosition.class)
                .subscribe(new Observer<RxPosition>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(RxPosition value) {
                        mTechnologyViewpager.setCurrentItem(value.getPosition());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setCurrentItem(int position) {
        mTechnologyViewpager.setCurrentItem(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = null;
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
