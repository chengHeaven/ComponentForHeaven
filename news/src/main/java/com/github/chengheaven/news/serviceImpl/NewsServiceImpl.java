package com.github.chengheaven.news.serviceImpl;

import android.support.v4.app.Fragment;

import com.github.chengheaven.componentservice.service.news.NewsService;
import com.github.chengheaven.news.view.news.NewsFragment;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class NewsServiceImpl implements NewsService {

    @Override
    public Fragment getNewsFragment() {
        return NewsFragment.newInstance();
    }

    @Override
    public void resume() {
        NewsFragment.resume();
    }

    @Override
    public void pause() {
        NewsFragment.pause();
    }

    @Override
    public void destroy() {
        NewsFragment.destroy();
    }
}
