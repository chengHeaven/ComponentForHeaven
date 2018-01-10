package com.github.chengheaven.componentservice.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements BaseView {
    @Override
    public void showWaiting() {
    }

    @Override
    public void hideWaiting() {
    }

    @Override
    public void updateLoadingProgress(int progress) {
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }
}
