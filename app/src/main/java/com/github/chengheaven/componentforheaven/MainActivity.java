package com.github.chengheaven.componentforheaven;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentlib.router.ui.UIRouter;
import com.github.chengheaven.componentservice.service.news.NewsService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Fragment fragment;
    FragmentTransaction ft;
    //    @BindView(R.id.add_movie)
    Button add;
    //    @BindView(R.id.intent_movie)
    Button intent;
    //    @BindView(R.id.add_intent_movie)
    Button add_intent;
    //    @BindView(R.id.delete_movie)
    Button delete;

    NewsService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ButterKnife.bind(this);

        add = findViewById(R.id.add_movie);
        delete = findViewById(R.id.delete_movie);
        intent = findViewById(R.id.intent_movie);
        add_intent = findViewById(R.id.add_intent_movie);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        intent.setOnClickListener(this);
        add_intent.setOnClickListener(this);

        Router router = Router.getInstance();

        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commit();
            fragment = null;
        }

        if (router.getService(NewsService.class.getSimpleName()) != null) {
            service = (NewsService) router.getService(NewsService.class.getSimpleName());
            fragment = service.getNewsFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_content, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (service != null) {
            service.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (service != null) {
            service.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service != null) {
            service.destroy();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_movie:
                Router.registerComponent(getApplicationContext(), "com.github.chengheaven.news.app.NewsApp");
                break;

            case R.id.intent_movie:
                UIRouter.getInstance().openUri(MainActivity.this, "component://news", null);
                break;

            case R.id.add_intent_movie:
                Router.registerComponent(getApplicationContext(), "com.github.chengheaven.news.app.NewsApp");
                UIRouter.getInstance().openUri(MainActivity.this, "component://news", null);
                break;

            case R.id.delete_movie:
                Router.unregisterComponent("com.github.chengheaven.news.app.NewsApp");
                break;

            default:
                break;
        }
    }
}
