package com.github.chengheaven.componentservice.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.componentservice.R;
import com.youth.banner.loader.ImageLoader;

/**
 * @author heaven_Cheng Created on 17/2/10.
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(R.drawable.img_one)
                .error(R.drawable.img_one)
                .crossFade(1000)
                .into(imageView);
    }
}
