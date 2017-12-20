package com.github.chengheaven.componentservice.customer.bezierBanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chengheaven.componentservice.utils.Utils;

public class BezierViewPager extends ViewPager {
    private boolean touchable = true;
    private ShadowTransformer cardShadowTransformer;
    private float mHeightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例
    private int mWidth = 0;
    private int mInterval = 8;
    private int mMaxFactor = 50;
    private float mZoomIn = 0.2f;
    private ImageView mBackground;
    private PagerAdapter mAdapter;
    private boolean mClipToPadding;

    public BezierViewPager(Context context) {
        super(context);
    }

    public BezierViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewPagerScroller scroller = new ViewPagerScroller(getContext());
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(this);
    }

    public void setTouchable(boolean isCanScroll) {
        this.touchable = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touchable && super.onTouchEvent(event);
    }


    public BezierViewPager setBackground(ImageView background) {
        this.mBackground = background;
        return this;
    }

    public BezierViewPager setTransformer(float zoomIn) {
        this.mZoomIn = zoomIn;
        return this;
    }

    public BezierViewPager setHeightRatio(float percent) {
        this.mHeightRatio = percent;
        return this;
    }

    public BezierViewPager setWidth(int width) {
        this.mWidth = width;
        return this;
    }

    public BezierViewPager setInterval(int interval) {
        this.mInterval = interval;
        return this;
    }

    public BezierViewPager setMaxFactor(int factor) {
        this.mMaxFactor = factor;
        return this;
    }

    public BezierViewPager setClipPadding(boolean b) {
        this.mClipToPadding = b;
        return this;
    }

    public void setAdapter(CardPagerAdapter adapter) {
        this.mAdapter = adapter;
        init();
    }

    private void init() {
        setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * mHeightRatio)));
        if (mBackground != null) {
            mBackground.setLayoutParams(new RelativeLayout.LayoutParams(mWidth,
                    (int) (mWidth * mHeightRatio) + Utils.dp2px(getContext(), 30)));
        }
        int mWidthPadding = mWidth / mInterval;
        float heightMore = (1.5f * mMaxFactor + dp2px(3)) - (mMaxFactor + dp2px(3)) * mHeightRatio;
        int mHeightPadding = (int) (mWidthPadding * mHeightRatio - heightMore) / 2;
        setPadding(mWidthPadding, mHeightPadding, mWidthPadding, mHeightPadding / 2);
        showTransformer(mZoomIn);
        setAdapter(mAdapter);
        setClipToPadding(mClipToPadding);
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return touchable && super.onInterceptTouchEvent(arg0);

    }

    public void showTransformer(float zoomIn) {
        if (CardAdapter.class.isInstance(getAdapter())) {
            if (cardShadowTransformer == null) {
                cardShadowTransformer = new ShadowTransformer();
                cardShadowTransformer.attachViewPager(this, (CardAdapter) getAdapter());
            }
            cardShadowTransformer.setZoomIn(zoomIn);

        }
    }
}
