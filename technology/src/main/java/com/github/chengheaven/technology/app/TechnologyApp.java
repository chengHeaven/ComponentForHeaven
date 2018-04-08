package com.github.chengheaven.technology.app;

import android.app.Application;
import android.content.Context;

import com.github.chengheaven.componentlib.app.IApp;
import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.technology.TechnologyService;
import com.github.chengheaven.technology.componuirouter.TechnologyUIRouter;
import com.github.chengheaven.technology.di.component.DaggerDataRepositoryComponent;
import com.github.chengheaven.technology.di.component.DataRepositoryComponent;
import com.github.chengheaven.technology.di.module.ApplicationModule;
import com.github.chengheaven.technology.serviceimpl.TechnologyServiceImpl;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyApp extends Application implements IApp {

    private Router mRouter = Router.getInstance();
    private UIRouter mUIRouter = UIRouter.getInstance();
    private TechnologyUIRouter mTechnologyUIRouter = TechnologyUIRouter.getInstance();
    private DataRepositoryComponent mDataRepositoryComponent;
    public Context mContext;
    private static TechnologyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }

    @Override
    public void create(Context context) {
        mUIRouter.registerUI(mTechnologyUIRouter);
        mContext = context;
        sInstance = this;
        mRouter.addService(TechnologyService.class.getSimpleName(), new TechnologyServiceImpl());
        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(mContext))
                .build();
    }

    @Override
    public void stop() {
        mUIRouter.unregisterUI(mTechnologyUIRouter);
        mRouter.removeService(TechnologyService.class.getSimpleName());
    }

    public static TechnologyApp getInstance() {
        return sInstance;
    }
}
