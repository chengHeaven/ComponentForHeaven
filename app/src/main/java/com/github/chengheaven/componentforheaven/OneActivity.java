package com.github.chengheaven.componentforheaven;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentservice.service.book.BookService;
import com.github.chengheaven.componentservice.service.movie.MovieService;
import com.github.chengheaven.componentservice.service.technology.TechnologyService;
import com.github.chengheaven.componentservice.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class OneActivity extends BaseActivity {

    FrameLayout mTitleMenu;
    ImageView mTitleGank;
    ImageView mTitleMovie;
    ImageView mTitleBook;
    ViewPager mViewpager;

    FragmentTransaction ft;
    Fragment mTechnologyFragment;
    Fragment mMovieFragment;
    Fragment mBookFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        mTitleMenu = findViewById(R.id.ll_title_menu);
        mTitleGank = findViewById(R.id.title_gank);
        mTitleMovie = findViewById(R.id.title_movie);
        mTitleBook = findViewById(R.id.title_book);
        mViewpager = findViewById(R.id.viewpager);

        mTitleGank.setOnClickListener(v -> {
            mViewpager.setCurrentItem(0);
        });

        mTitleMovie.setOnClickListener(v -> {
            mViewpager.setCurrentItem(1);
        });

        mTitleBook.setOnClickListener(v -> {
            mViewpager.setCurrentItem(2);
        });

        List<Fragment> fragments = new ArrayList<>();
        Router router = Router.getInstance();

        if (router.getService(TechnologyService.class.getSimpleName()) != null) {
            TechnologyService technologyService = (TechnologyService) router.getService(TechnologyService.class.getSimpleName());
            mTechnologyFragment = technologyService.getTechnologyFragment();
        }

        if (router.getService(MovieService.class.getSimpleName()) != null) {
            MovieService movieService = (MovieService) router.getService(MovieService.class.getSimpleName());
            mMovieFragment = movieService.getMovieBaseFragment();
        }

        if (router.getService(BookService.class.getSimpleName()) != null) {
            BookService bookService = (BookService) router.getService(BookService.class.getSimpleName());
            mBookFragment = bookService.getBookBaseFragment();
        }


        fragments.add(mTechnologyFragment);
        fragments.add(mMovieFragment);
        fragments.add(mBookFragment);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.one_act;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
