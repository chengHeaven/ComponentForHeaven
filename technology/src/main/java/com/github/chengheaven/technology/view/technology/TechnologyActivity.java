package com.github.chengheaven.technology.view.technology;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.app.TechnologyApp;
import com.github.chengheaven.technology.di.component.DaggerTechnologyComponent;
import com.github.chengheaven.technology.di.module.TechnologyModule;
import com.github.chengheaven.technology.presenter.technology.TechnologyPresenter;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class TechnologyActivity extends AppCompatActivity {

    @Inject
    TechnologyPresenter mCodePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_act);

        TechnologyFragment technologyFragment = (TechnologyFragment) getSupportFragmentManager().findFragmentById(R.id.code_content_frame);
        if (technologyFragment == null) {
            technologyFragment = TechnologyFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.code_content_frame, technologyFragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerTechnologyComponent.builder()
                .dataRepositoryComponent(TechnologyApp.getInstance().getDataRepositoryComponent())
                .technologyModule(new TechnologyModule(technologyFragment))
                .build()
                .inject(this);
    }
}
