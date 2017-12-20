package com.github.chengheaven.componentservice.customer;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.componentservice.application.BaseApplication;
import com.github.chengheaven.componentservice.utils.GlideCircleTransform;

public class ImageLoader {

    private Activity mActivity;
    private Fragment mFragment;
    private RequestManager mManager;
    private RequestListener mListener;
    private int mWidth;
    private int mHeight;

    public ImageLoader(Activity activity, int width, int height) {
        this.mActivity = activity;
        mManager = Glide.with(activity);
        mWidth = width;
        mHeight = height;
    }

    public ImageLoader(Fragment fragment, int width, int height) {
        this.mFragment = fragment;
        mManager = Glide.with(fragment);
        mWidth = width;
        mHeight = height;
        mListener = new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                Log.e("glide......", e.getLocalizedMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.e("glide.....", "load true");
                return false;
            }
        };

    }

    public RequestManager getManager() {
        return mManager;
    }

    public void loadImage(Object Object, ImageView imageView) {
        mManager.load(Object).override(mWidth, mHeight).listener(mListener).into(imageView);
    }

    public void loadImage(Object object, int error, int placeHolder, ImageView imageView) {
        mManager.load(object)
                .error(error)
                .placeholder(placeHolder)
                .into(imageView);
    }

    public void loadCircularImage(Object object, ImageView imageView) {
        mManager.load(object)
                .transform(new GlideCircleTransform(BaseApplication.getContext()))
                .into(imageView);
    }

    public void loadCircularImage(Object object, int error, int placeHolder, ImageView imageView) {
        mManager.load(object)
                .error(error)
                .placeholder(placeHolder)
                .transform(new GlideCircleTransform(BaseApplication.getContext()))
                .into(imageView);
    }

    public void pauseRequests() {
        mManager.pauseRequests();
    }

    public void pauseRequestsRecursive() {
        mManager.pauseRequestsRecursive();
    }

    public void resumeRequests() {
        mManager.resumeRequests();
    }

    public void resumeRequestsRecursive() {
        mManager.resumeRequestsRecursive();
    }
}
