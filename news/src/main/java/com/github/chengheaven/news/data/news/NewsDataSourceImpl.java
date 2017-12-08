package com.github.chengheaven.news.data.news;

import android.content.Context;

public class NewsDataSourceImpl implements NewsDataSource {
    private Context mContext;

    public NewsDataSourceImpl(Context context) {
        this.mContext = context;
    }
}
