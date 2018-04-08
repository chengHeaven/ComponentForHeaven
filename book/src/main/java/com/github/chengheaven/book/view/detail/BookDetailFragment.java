package com.github.chengheaven.book.view.detail;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.book.R;
import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.presenter.detail.BookDetailContract;
import com.github.chengheaven.book.util.ResourceUtil;
import com.github.chengheaven.componentservice.customer.statusbar.StatusBarUtil;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookDetailFragment extends BaseFragment implements BookDetailContract.View {

    ImageView mBackground;
    ImageView mImage;
    TextView mAuthor;
    TextView mScore;
    TextView mScorePeople;
    TextView mDay;
    TextView mPress;
    LinearLayout mErrorBar;
    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    TextView mSummary;
    TextView mAuthorSummary;
    TextView mCatalog;
    LinearLayout mContent;

    private AnimationDrawable mAnimationDrawable;

    private int slidingDistance;
    private int imageBgHeight;


    private BookDetailContract.Presenter mPresenter;

    public static BookDetailFragment newInstance() {
        return new BookDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment, container, false);

        mBackground = view.findViewById(R.id.book_detail_background);
        mImage = view.findViewById(R.id.book_detail_image);
        mAuthor = view.findViewById(R.id.book_detail_author);
        mScore = view.findViewById(R.id.book_detail_score);
        mScorePeople = view.findViewById(R.id.book_detail_score_people);
        mDay = view.findViewById(R.id.book_detail_day);
        mPress = view.findViewById(R.id.book_detail_press);

        mSummary = view.findViewById(R.id.book_detail_summary);
        mAuthorSummary = view.findViewById(R.id.book_detail_author_summary);
        mCatalog = view.findViewById(R.id.book_detail_catalog);
        mContent = view.findViewById(R.id.book_detail_fragment_content);

        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);

        showLoading();


        mPresenter.start();
        mPresenter.requestBookDetailData(view);
        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (BookDetailContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mContent.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateTitleView(BookData.BookBean result) {
        initSlideShapeTheme(mBackground);
        StringBuilder sb = new StringBuilder();
        for (String author : result.getAuthor()) {
            sb.append(author).append(" / ");
        }
        ((BookDetailActivity) getActivity()).setTitle(result.getTitle(), "".equals(sb.toString()) ? "" : sb.substring(0, sb.length() - 3));

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((BookDetailActivity) getActivity()).setToolbarBackground(Color.TRANSPARENT);
                ((BookDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
                ((BookDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                return false;
            }
        }).into(((BookDetailActivity) getActivity()).getBackgroundImage());

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((BookDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                mBackground.setImageAlpha(255);
                mBackground.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(mBackground);

        Glide.with(getActivity())
                .load(result.getImages().getLarge())
                .into(mImage);

        mAuthor.setText("".equals(sb.toString()) ? "" : sb.substring(0, sb.length() - 3));
        mScore.setText(String.format("评分：%s", result.getRating().getAverage()));
        mScorePeople.setText(String.format("%s 人评分", result.getRating().getNumRaters()));
        mDay.setText(String.format("出版时间：%s","".equals(result.getPubDate()) ? "暂无" : result.getPubDate()));
        mPress.setText(String.format("出版社：%s","".equals(result.getPublisher()) ? "暂无" : result.getPublisher()));
    }

    @Override
    public void updateView(BookData.BookBean result) {
        mSummary.setText("".equals(result.getSummary()) ? "暂无" : result.getSummary());
        mAuthorSummary.setText("".equals(result.getAuthorIntro()) ? "暂无" : result.getAuthorIntro());
        mCatalog.setText("".equals(result.getCatalog()) ? "暂无" : result.getCatalog());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void initSlideShapeTheme(ImageView mHeaderBg) {

        // toolbar 的高
        int toolbarHeight = ((BookDetailActivity) getActivity()).getToolbar().getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(getActivity());

        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = ((BookDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) ((BookDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        ((BookDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
        StatusBarUtil.setTranslucentImageHeader(getActivity(), 0, ((BookDetailActivity) getActivity()).getToolbar());

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(getActivity()), 0, 0);

            ViewGroup.LayoutParams imgItemBgparams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgparams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }

    private void initScrollViewListener() {
        // 为了兼容23以下
        ((BookDetailActivity) getActivity()).getBaseScroll().setOnScrollChangeListener((scrollX, scrollY, oldScrollX, oldScrollY) -> scrollChangeHeader(scrollY));
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (ResourceUtil.getDimens(R.dimen.book_nav_bar_height) + StatusBarUtil.getStatusBarHeight(getActivity()));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (ResourceUtil.getDimens(R.dimen.book_base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = ((BookDetailActivity) getActivity()).getBackgroundImage().getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            ((BookDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            ((BookDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        }
    }
}
