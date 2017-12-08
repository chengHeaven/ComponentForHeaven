package com.github.chengheaven.componentservice.customer.bezierBanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.componentservice.R;

import java.util.ArrayList;
import java.util.List;


public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<Object> mData;
    private Context mContext;
    private int MaxElevationFactor = 50;

    @Override
    public int getMaxElevationFactor() {
        return MaxElevationFactor;
    }

    @Override
    public void setMaxElevationFactor(int px) {
        this.MaxElevationFactor = px;
    }

    public CardPagerAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        this.mContext = context;
    }

    public void addUrl(List<Object> urlList) {
        mData.addAll(urlList);
        for (int i = 0; i < urlList.size(); i++) {
            mViews.add(null);
        }
    }


    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.base_bezier_adapter, container, false);
        view.setOnClickListener(v -> {
            if (cardItemClickListener != null) {
                cardItemClickListener.onClick(v, position);
            }
        });
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        cardView.setMaxCardElevation(MaxElevationFactor);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Object imgUrl, View view) {
        ImageView iv = view.findViewById(R.id.item_iv);
        Glide.with(mContext).load(imgUrl).into(iv);

    }

    private OnCardItemClickListener cardItemClickListener;

    public interface OnCardItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnCardItemClickListener(OnCardItemClickListener cardItemClickListener) {
        this.cardItemClickListener = cardItemClickListener;
    }

}
