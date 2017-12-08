package com.github.chengheaven.componentservice.customer.bezierBanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class BezierViewPager extends ViewPager {
    private boolean touchable = true;
    private ShadowTransformer cardShadowTransformer;
    private float mHeightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例
    private int mWidth = 0;
    private int mInterval = 8;
    private int mMaxFactor = 50;

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

    public void setHeightRatio(float heightRatio) {
        this.mHeightRatio = heightRatio;
        init();
    }

    // view 的 宽
    public void setWidth(int width) {
        this.mWidth = width;
        init();
    }

    // 图片间隔大小
    public void setMaxElevationFactor(int px) {
        this.mMaxFactor = px;
        init();
    }

    // 左右空隙是 View 的 几分之一
    public void setInterval(int mInterval) {
        this.mInterval = mInterval;
        init();
    }

    private void init() {
        setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * mHeightRatio)));
        int mWidthPadding = mWidth / mInterval;
        float heightMore = (1.5f * mMaxFactor + dp2px(3)) - (mMaxFactor + dp2px(3)) * mHeightRatio;
        int mHeightPadding = (int) (mWidthPadding * mHeightRatio - heightMore) / 2;
        setPadding(mWidthPadding, mHeightPadding, mWidthPadding, mHeightPadding / 2);
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
