package com.github.chengheaven.news.app;

import android.app.Application;
import android.content.Context;

import com.github.chengheaven.componentlib.app.IApp;
import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.news.NewsService;
import com.github.chengheaven.news.compouiriuter.NewsUIRouter;
import com.github.chengheaven.news.di.component.DaggerDataRepositoryComponent;
import com.github.chengheaven.news.di.component.DataRepositoryComponent;
import com.github.chengheaven.news.di.module.ApplicationModule;
import com.github.chengheaven.news.serviceImpl.NewsServiceImpl;

/**
 * @author Heaven・Cheng Created on 2017/9/26.
 */

public class NewsApp extends Application implements IApp {

    private Router router = Router.getInstance();
    private UIRouter uiRouter = UIRouter.getInstance();
    private NewsUIRouter newsUIRouter = NewsUIRouter.getInstance();
    private DataRepositoryComponent mDataRepositoryComponent;
    public Context mContext;
    private static NewsApp sInstance;

    @Override
    public void create(Context context) {
        uiRouter.registerUI(newsUIRouter);
        mContext = context;
        sInstance = this;
        router.addService(NewsService.class.getSimpleName(), new NewsServiceImpl());
        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(mContext))
                .build();
    }

    @Override
    public void stop() {
        uiRouter.unregisterUI(newsUIRouter);
        router.removeService(NewsService.class.getSimpleName());
    }

    public static NewsApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }

}
