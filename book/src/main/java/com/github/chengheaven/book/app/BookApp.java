package com.github.chengheaven.book.app;

import android.app.Application;
import android.content.Context;

import com.github.chengheaven.book.componuirouter.BookUiRouter;
import com.github.chengheaven.book.di.component.DaggerDataRepositoryComponent;
import com.github.chengheaven.book.di.component.DataRepositoryComponent;
import com.github.chengheaven.book.di.module.ApplicationModule;
import com.github.chengheaven.book.serviceimpl.BookServiceImpl;
import com.github.chengheaven.componentlib.app.IApp;
import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.book.BookService;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookApp extends Application implements IApp {

    private Router mRouter = Router.getInstance();
    private UIRouter mUIRouter = UIRouter.getInstance();
    private BookUiRouter mBookUiRouter = BookUiRouter.getInstance();
    private DataRepositoryComponent mDataRepositoryComponent;
    public Context mContext;
    private static BookApp sInstance;

    @Override
    public void create(Context context) {
        mUIRouter.registerUI(mBookUiRouter);
        mContext = context;
        sInstance = this;
        mRouter.addService(BookService.class.getSimpleName(),new BookServiceImpl());
        mDataRepositoryComponent = DaggerDataRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(mContext))
                .build();
    }

    @Override
    public void stop() {
        mUIRouter.unregisterUI(mBookUiRouter);
        mRouter.removeService(BookService.class.getSimpleName());
    }

    public static BookApp getInstance() {
        return sInstance;
    }

    public DataRepositoryComponent getDataRepositoryComponent() {
        return mDataRepositoryComponent;
    }

}
