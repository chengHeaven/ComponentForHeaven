package com.github.chengheaven.news.runalone.view.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chengheaven.componentservice.customer.AppBarStateChangeListener;
import com.github.chengheaven.componentservice.customer.bezierBanner.BezierDot;
import com.github.chengheaven.componentservice.customer.bezierBanner.BezierViewPager;
import com.github.chengheaven.componentservice.customer.bezierBanner.CardPagerAdapter;
import com.github.chengheaven.componentservice.customer.statusbar.StatusBarUtil;
import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.news.R;
import com.github.chengheaven.news.runalone.presenter.news.NewsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View {

    ImageView mBackground;
    BezierViewPager mViewpager;
    BezierDot mDot;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout mAppBarLayout;
    CoordinatorLayout mRootLayout;
    private List<Object> imageList;

    private NewsContract.Presenter mPresenter;

    public NewsFragment() {

    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_test_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((NewsTestActivity) getActivity()).setSupportActionBar(toolbar);
        StatusBarUtil.setTranslucentForImageView(getActivity(), 0, toolbar);

        mCollapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        mAppBarLayout = view.findViewById(R.id.app_bar_layout);
        mRootLayout = view.findViewById(R.id.rootLayout);

        mCollapsingToolbarLayout.setTitleEnabled(false);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    mViewpager.setVisibility(View.GONE);
                    mDot.setVisibility(View.GONE);
                } else {
                    mViewpager.setVisibility(View.VISIBLE);
                    mDot.setVisibility(View.VISIBLE);
                }
            }
        });

        mBackground = view.findViewById(R.id.news_banner_background);
        mViewpager = view.findViewById(R.id.news_banner_viewpager);
        mDot = view.findViewById(R.id.news_banner_dot);


        initImageList();
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(getContext());
        cardPagerAdapter.addUrl(imageList);
        cardPagerAdapter.setOnCardItemClickListener((v, position) ->
                Snackbar.make(v, "click" + position, Snackbar.LENGTH_SHORT).show());

        mViewpager.setWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth());
        mViewpager.setInterval(8);
        mViewpager.setClipToPadding(false);
        mViewpager.setAdapter(cardPagerAdapter);
        mViewpager.showTransformer(0.2f);

//        ViewPagerScroller scroller = new ViewPagerScroller(getContext());
//        scroller.setScrollDuration(1500);
//        scroller.initViewPagerScroll(mViewpager);

        mDot.setInterval(10);
        mDot.setRadius(20);
        mDot.setDelay(2000);
        mDot.attachToViewPager(mViewpager, mBackground, imageList);
        mDot.start();

        mBackground.setLayoutParams(new RelativeLayout.LayoutParams(mViewpager.getLayoutParams().width,
                mViewpager.getLayoutParams().height + Utils.dp2px(getContext(), 30)));


        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(HotFragment.newInstance());
        fragments.add(HotFragment.newInstance());
        fragments.add(HotFragment.newInstance());

        titles.add("热门");
        titles.add("娱乐");
        titles.add("新闻");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
//        mPager = view.findViewById(R.id.viewpager);
//        mTab = view.findViewById(R.id.tab);
//        mTab.setTabMode(TabLayout.MODE_FIXED);
//        mTab.setupWithViewPager(mPager);
//        mPager.setAdapter(adapter);
//        mTab.setTabsFromPagerAdapter(adapter);
//        mTab.addOnTabSelectedListener(this);

        return view;
    }

    public void initImageList() {
        imageList = new ArrayList<>();
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2946550071,381041431&fm=11&gp=0.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2946550071,381041431&fm=11&gp=0.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");
    }


    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (NewsContract.Presenter) presenter;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        private List<Fragment> mFragments;
        private List<String> mTitles;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
