package com.github.chengheaven.news.runalone.view.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.news.R;


public class HotFragment extends BaseFragment {


    public HotFragment() {
    }

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_hot_fragment, container, false);


        return view;
    }
}
