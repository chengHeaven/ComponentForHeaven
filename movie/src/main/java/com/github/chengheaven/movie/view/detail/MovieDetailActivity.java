package com.github.chengheaven.movie.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chengheaven.componentservice.customer.MyNestedScrollView;
import com.github.chengheaven.componentservice.view.BaseActivity;
import com.github.chengheaven.componentservice.view.webview.WebViewActivity;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.app.MovieApp;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.di.component.DaggerMovieDetailComponent;
import com.github.chengheaven.movie.di.module.MovieDetailModule;
import com.github.chengheaven.movie.presenter.detail.MovieDetailPresenter;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieDetailActivity extends BaseActivity {

    Toolbar mToolbar;

    MyNestedScrollView mMnsBase;
    ImageView mMovieImage;
    TextView mMovieName;
    TextView mMoviePerformer;

    @Inject
    MovieDetailPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.movie_detail_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMnsBase = findViewById(R.id.mns_base);
        mMovieImage = findViewById(R.id.movie_detail_act_image);
        mMovieName = findViewById(R.id.movie_detail_name);
        mMoviePerformer = findViewById(R.id.movie_detail_performer);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setBackgroundResource(R.color.transparent);


        MovieBean.SubjectsBean bean = (MovieBean.SubjectsBean) getIntent().getBundleExtra("item")
                .getSerializable("movieBean");

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_detail_content_frame);

        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.movie_detail_content_frame, movieDetailFragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerMovieDetailComponent.builder()
                .dataRepositoryComponent(MovieApp.getInstance().getDataRepositoryComponent())
                .movieDetailModule(new MovieDetailModule(movieDetailFragment))
                .build()
                .inject(this);

        mPresenter.setData(bean);
    }

    public void setTitle(String movieName, String performer) {
        mMovieName.setText(movieName);
        mMoviePerformer.setText(performer);
    }

    public void setToolbarBackground(int color) {
        mToolbar.setBackgroundColor(color);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public ImageView getBackgroundImage() {
        return mMovieImage;
    }

    public MyNestedScrollView getBaseScroll() {
        return mMnsBase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_more_info) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("title", mPresenter.getData().getTitle());
            intent.putExtra("url", mPresenter.getData().getAlt());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isDoubleExit() {
        return false;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
