package com.github.chengheaven.technology.view.technology;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.presenter.technology.AndroidContract;
import com.github.chengheaven.technology.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class AndroidFragment extends BaseFragment implements AndroidContract.View {

    private XRecyclerView mAndroidRecycler;

    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    LinearLayout mErrorBar;
    private AnimationDrawable mAnimation;

    private int page = 1;
    private int per = 20;

    private AndroidAdapter mAdapter;

    private AndroidContract.Presenter mPresenter;

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (AndroidContract.Presenter) presenter;
    }

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.technology_android_fragment, container, false);

        mAndroidRecycler = view.findViewById(R.id.android_recycler);

        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        showLoading();

        mAndroidRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AndroidAdapter();
        mAndroidRecycler.setAdapter(mAdapter);

        if (mPresenter.getAndroidDataFromLocal() != null && mPresenter.getAndroidDataFromLocal().size() != 0) {
            hideLoading();
            mAdapter.refresh(mPresenter.getAndroidDataFromLocal());
        } else {
            mPresenter.getAndroidData(view, null, getString(R.string.technology_android), page, per);
        }

        mAndroidRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getAndroidData(view, null, getString(R.string.technology_android), page, per);
            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getAndroidData(view, getString(R.string.technology_refresh), getString(R.string.technology_android), page, per);
            }
        });

        mErrorBar.setOnClickListener(v -> {
            hideError();
            showLoading();
            mPresenter.getAndroidData(view, null, getString(R.string.technology_android), page, 20);
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && mPresenter.getAndroidDataFromLocal() != null
//                && mPresenter.getAndroidDataFromLocal().size() != 0) {
//            mAdapter.refresh(mPresenter.getAndroidDataFromLocal());
//        }
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimation = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimation.start();
    }

    @Override
    public void hideLoading() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimation.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimation.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @Override
    public void updateList(List<GankData.ResultsBean> list) {
        mAndroidRecycler.refreshComplete();
        mAdapter.update(list);
    }

    @Override
    public void refreshList(List<GankData.ResultsBean> list) {
        mAndroidRecycler.refreshComplete();
        mAdapter.refresh(list);
    }

    class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.ViewHolder> {

        private List<GankData.ResultsBean> mList = new ArrayList<>();

        void update(List<GankData.ResultsBean> list) {
            if (list != null && list.size() != 0) {
                mList.addAll(list);
                notifyDataSetChanged();
            }
        }

        void refresh(List<GankData.ResultsBean> list) {
            mList.clear();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technology_android_fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mWelfare.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.GONE);
            if (mList.get(position).getImages() != null) {
                holder.mImage.setVisibility(View.VISIBLE);
                Glide.with(getContext())
                        .load(mList.get(position).getImages().get(0))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .centerCrop()
                        .into(holder.mImage);
            }
            holder.mTitle.setText(mList.get(position).getDesc());
            holder.mWho.setText(mList.get(position).getWho());
            holder.mType.setText(mList.get(position).getType());
            holder.mDate.setText(getDate(mList.get(position).getPublishedAt()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", mList.get(position).getUrl());
                intent.putExtra("title", getString(R.string.technology_loading));
                startActivity(intent);
            });
        }

        String getDate(String date) {
            String[] s = date.split("-");
            date = s[1] + "-" + s[2].substring(0, 2);
            return date;
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTitle;
            ImageView mImage;
            ImageView mWelfare;
            TextView mWho;
            TextView mType;
            TextView mDate;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.android_item_title);
                mImage = itemView.findViewById(R.id.android_item_image);
                mWelfare = itemView.findViewById(R.id.android_item_welfare);
                mWho = itemView.findViewById(R.id.android_item_who);
                mType = itemView.findViewById(R.id.android_item_type);
                mDate = itemView.findViewById(R.id.android_item_date);
            }
        }
    }
}
