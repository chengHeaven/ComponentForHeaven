package com.github.chengheaven.componentforheaven;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chengheaven.componentlib.router.Router;
import com.github.chengheaven.componentservice.service.book.BookService;
import com.github.chengheaven.componentservice.service.movie.MovieService;
import com.github.chengheaven.componentservice.service.technology.TechnologyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_menu)
    ImageView mTitleMenu;
    @BindView(R.id.ll_title_menu)
    FrameLayout mLlTitleMenu;
    @BindView(R.id.title_gank)
    ImageView mTitleGank;
    @BindView(R.id.title_movie)
    ImageView mTitleMovie;
    @BindView(R.id.title_book)
    ImageView mTitleBook;
    @BindView(R.id.ll_search)
    RelativeLayout mLlSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.gank_content)
    FrameLayout mGankContent;
    @BindView(R.id.movie_content)
    FrameLayout mMovieContent;
    @BindView(R.id.book_content)
    FrameLayout mBookContent;

    FragmentTransaction mFt;
    Fragment mTsFragment;
    Fragment mMsFragment;
    Fragment mBsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Router router = Router.getInstance();

        if (mTsFragment != null) {
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.remove(mTsFragment).commitAllowingStateLoss();
            mTsFragment = null;
        }

        if (router.getService(TechnologyService.class.getSimpleName()) != null) {
            TechnologyService ts = (TechnologyService) router.getService(TechnologyService.class.getSimpleName());
            mTsFragment = ts.getTechnologyFragment();
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.add(R.id.gank_content, mTsFragment).commitAllowingStateLoss();
        }

        if (mMsFragment != null) {
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.remove(mMsFragment).commitAllowingStateLoss();
            mMsFragment = null;
        }

        if (router.getService(MovieService.class.getSimpleName()) != null) {
            MovieService ms = (MovieService) router.getService(MovieService.class.getSimpleName());
            mMsFragment = ms.getMovieBaseFragment();
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.add(R.id.movie_content, mMsFragment).commitAllowingStateLoss();
        }

        if (mBsFragment != null) {
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.remove(mBsFragment).commitAllowingStateLoss();
            mBsFragment = null;
        }

        if (router.getService(BookService.class.getSimpleName()) != null) {
            BookService bs = (BookService) router.getService(BookService.class.getSimpleName());
            mBsFragment = bs.getBookBaseFragment();
            mFt = getSupportFragmentManager().beginTransaction();
            mFt.add(R.id.book_content, mBsFragment).commitAllowingStateLoss();
        }
    }

    @OnClick({R.id.title_gank, R.id.title_movie, R.id.title_book})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_gank:
                mGankContent.setVisibility(View.VISIBLE);
                mMovieContent.setVisibility(View.GONE);
                mBookContent.setVisibility(View.GONE);

                //if intent activity
//                Router.registerComponent(getApplicationContext(), "com.github.chengheaven.technology.app.TechnologyApp");
//                UIRouter.getInstance().openUri(MainActivity.this, "component://technology", null);
                break;

            case R.id.title_movie:
                mGankContent.setVisibility(View.GONE);
                mMovieContent.setVisibility(View.VISIBLE);
                mBookContent.setVisibility(View.GONE);

                //if intent activity
//                Router.registerComponent(getApplicationContext(), "com.github.chengheaven.movie.app.MovieApp");
//                UIRouter.getInstance().openUri(MainActivity.this, "component://movie", null);
                break;

            case R.id.title_book:
                mGankContent.setVisibility(View.GONE);
                mMovieContent.setVisibility(View.GONE);
                mBookContent.setVisibility(View.VISIBLE);

                //if intent activity
//                Router.registerComponent(getApplicationContext(), "com.github.chengheaven.book.app.BookApp");
//                UIRouter.getInstance().openUri(MainActivity.this, "component://book", null);
                break;

            default:
                break;
        }
    }
}
