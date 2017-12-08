package com.github.chengheaven.componentservice.view;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);

    void showWaiting();

    void hideWaiting();

    void updateLoadingProgress(int progress);

    void toastMessage(String msg);
}
