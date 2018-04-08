package com.github.chengheaven.componentforheaven;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.technology.TechnologyService;

/**
 * @author Heaven_Cheng Created on 2017/12/26.
 */
public class TestActivity extends AppCompatActivity {

    FragmentTransaction ft;
    Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            Router.registerComponent(getApplicationContext(), "com.github.chengheaven.movie.app.MovieApp");
            UIRouter.getInstance().openUri(TestActivity.this, "component://movie", null);
        });

        Router router = Router.getInstance();

        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commitAllowingStateLoss();
            fragment = null;
        }

        if (router.getService(TechnologyService.class.getSimpleName()) != null) {
            TechnologyService technologyService = (TechnologyService) router.getService(TechnologyService.class.getSimpleName());
            fragment = technologyService.getTechnologyFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.test_frame, fragment).commitAllowingStateLoss();
        }

    }
}
