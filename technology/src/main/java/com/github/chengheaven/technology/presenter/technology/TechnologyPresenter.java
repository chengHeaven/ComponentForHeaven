package com.github.chengheaven.technology.presenter.technology;

import com.github.chengheaven.componentservice.utils.RxBus;
import com.github.chengheaven.technology.bean.rx.RxPosition;
import com.github.chengheaven.technology.data.DataRepository;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyPresenter implements TechnologyContract.Presenter {

    private final TechnologyContract.View mView;
    private final DataRepository mDataRepository;

    private Disposable mDisposable;

    @Inject
    TechnologyPresenter(TechnologyContract.View view, DataRepository dataRepository) {
        this.mView = view;
        this.mDataRepository = dataRepository;
    }

    @Inject
    @Override
    public void setupPresenterToView() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        registerPosition();
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
                        mView.setCurrentItem(value.getPosition());
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
    public void clear() {
        mDataRepository.clear();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }
}
