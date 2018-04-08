package com.github.chengheaven.book.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.app.BookApp;
import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.di.component.DaggerBookDetailComponent;
import com.github.chengheaven.book.di.module.BookDetailModule;
import com.github.chengheaven.book.presenter.detail.BookDetailPresenter;
import com.github.chengheaven.componentservice.customer.MyNestedScrollView;
import com.github.chengheaven.componentservice.view.BaseActivity;
import com.github.chengheaven.componentservice.view.webview.WebViewActivity;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookDetailActivity extends BaseActivity {

    Toolbar mToolbar;

    MyNestedScrollView mMnsBase;
    ImageView mBookImage;
    TextView mBookName;
    TextView mBookPerformer;

    @Inject
    BookDetailPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.book_detail_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMnsBase = findViewById(R.id.mns_base);
        mBookImage = findViewById(R.id.book_detail_act_image);
        mBookName = findViewById(R.id.book_detail_name);
        mBookPerformer = findViewById(R.id.book_detail_author);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setBackgroundResource(R.color.transparent);

        BookDetailFragment fragment = (BookDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.book_detail_content_frame);

        if (fragment == null) {
            fragment = BookDetailFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.book_detail_content_frame, fragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerBookDetailComponent.builder()
                .dataRepositoryComponent(BookApp.getInstance().getDataRepositoryComponent())
                .bookDetailModule(new BookDetailModule(fragment))
                .build()
                .inject(this);

        BookData.BookBean data = (BookData.BookBean) getIntent().getBundleExtra("item")
                .getSerializable("bookBean");

        mPresenter.setData(data);
    }

    public void setTitle(String bookName, String performer) {
        mBookName.setText(bookName);
        mBookPerformer.setText(performer);
    }

    public void setToolbarBackground(int color) {
        mToolbar.setBackgroundColor(color);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public ImageView getBackgroundImage() {
        return mBookImage;
    }

    public MyNestedScrollView getBaseScroll() {
        return mMnsBase;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu_book_detail, menu);
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
