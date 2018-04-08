package com.github.chengheaven.book.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.chengheaven.book.R;
import com.github.chengheaven.book.app.BookApp;
import com.github.chengheaven.book.di.component.DaggerBookComponent;
import com.github.chengheaven.book.di.module.BookModule;
import com.github.chengheaven.book.presenter.BookPresenter;

import javax.inject.Inject;

public class BookActivity extends AppCompatActivity {

    @Inject
    BookPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_act);

        BookFragment fragment = (BookFragment) getSupportFragmentManager()
                .findFragmentById(R.id.book_content_frame);

        if (fragment == null) {
            fragment = BookFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.book_content_frame, fragment);
            transaction.commitAllowingStateLoss();
        }

        DaggerBookComponent.builder()
                .dataRepositoryComponent(BookApp.getInstance().getDataRepositoryComponent())
                .bookModule(new BookModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
