package com.github.chengheaven.movie.app;

import android.app.Application;
import android.content.Context;

import com.github.chengheaven.componentlib.app.IApp;
import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.movie.MovieService;
import com.github.chengheaven.movie.componuirouter.MovieUIRouter;
import com.github.chengheaven.movie.di.component.DaggerDataRepositoryComponent;
import com.github.chengheaven.movie.di.component.DataRepositoryComponent;
import com.github.chengheaven.movie.di.module.ApplicationModule;
import com.github.chengheaven.movie.serviceimpl.MovieServiceImpl;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class MovieApp extends Application implements IApp {

    private Router mRouter = Router.getInstance();
    private UIRouter mUIRouter = UIRouter.getInstance();
    private MovieUIRouter mMovieUIRouter = MovieUIRouter.getInstance();
    private DataRepositoryComponent mDataRepositoryComponent;
    public Context mContext;
    private static MovieApp sInstance;

    @Override
    public void create(Context context) {
        mUIRouter.registerUI(mMovieUIRouter);
        mContext = context;
        sInstance = this;
        mRouter.addService(MovieService.class.getSimpleName(),new MovieServiceImpl());
        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(mContext))
                .build();
    }

    @Override
    public void stop() {
        mUIRouter.unregisterUI(mMovieUIRouter);
        mRouter.removeService(MovieService.class.getSimpleName());
    }

    public static MovieApp getInstance() {
        return sInstance;
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }

}
