package com.github.chengheaven.technology.view.welfareimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.app.TechnologyApp;
import com.github.chengheaven.technology.di.component.DaggerWelfareImageComponent;
import com.github.chengheaven.technology.di.module.WelfareImageModule;
import com.github.chengheaven.technology.presenter.welfareimage.WelfareImagePresenter;
import com.github.chengheaven.componentservice.view.BaseActivity;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/8.
 */
public class WelfareImageActivity extends BaseActivity {

    @Inject
    WelfareImagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WelfareImageFragment fragment = (WelfareImageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.welfare_image_content_frame);

        if (fragment == null) {
            fragment = WelfareImageFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.welfare_image_content_frame, fragment);
            transaction.commit();
        }

        DaggerWelfareImageComponent.builder()
                .dataRepositoryComponent(TechnologyApp.getInstance().getDataRepositoryComponent())
                .welfareImageModule(new WelfareImageModule(fragment))
                .build()
                .inject(this);

        int position = getIntent().getIntExtra("position", 0);
        mPresenter.setPosition(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.code_welfare_image_act;
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
