package com.github.chengheaven.technology.view.technology;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.presenter.technology.WelfareContract;
import com.github.chengheaven.technology.view.welfareimage.WelfareImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class WelfareFragment extends BaseFragment implements WelfareContract.View {

    XRecyclerView mWelfareRecycler;

    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    LinearLayout mErrorBar;
    private AnimationDrawable mAnimation;

    private WelfareAdapter mAdapter;
    private int page = 1;

    private WelfareContract.Presenter mPresenter;

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (WelfareContract.Presenter) presenter;
    }

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.technology_welfare_fragment, container, false);

        mWelfareRecycler = view.findViewById(R.id.welfare_recycler);
        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mErrorBar = view.findViewById(R.id.ll_error_refresh);

        showLoading();
        mWelfareRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mWelfareRecycler.setPullRefreshEnabled(false);
        mAdapter = new WelfareAdapter();
        mWelfareRecycler.setAdapter(mAdapter);

        page = 1;
        if (mPresenter.getWelfareImageFromLocal() != null && mPresenter.getWelfareImageFromLocal().size() != 0) {
            mAdapter.update(mPresenter.getWelfareImageFromLocal());
        } else {
            mPresenter.getWelfare(view, getString(R.string.technology_welfare_chinese), page, 20);
        }
        mWelfareRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getWelfare(view, getString(R.string.technology_welfare_chinese), page, 20);
            }
        });

        mErrorBar.setOnClickListener(v -> {
            hideError();
            showLoading();
            mPresenter.getWelfare(view, getString(R.string.technology_welfare_chinese), page, 20);
        });


        return view;
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
    public void updateList(List<String> urls) {
        mWelfareRecycler.refreshComplete();
        mAdapter.add(urls);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.clear();
    }

    class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {

        private List<String> mUrls = new ArrayList<>();

        void add(List<String> list) {
            if (list != null && list.size() != 0) {
                mUrls.addAll(list);
                notifyDataSetChanged();
            }
        }

        void update(List<String> list) {
            mUrls.clear();
            mUrls = list;
            notifyDataSetChanged();
        }

        void clear() {
            mUrls.clear();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technology_welfare_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mUrls.get(position))
                    .asBitmap()
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .into(holder.mWelfare);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WelfareImageActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mUrls == null ? 0 : mUrls.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mWelfare;

            ViewHolder(View itemView) {
                super(itemView);
                mWelfare = itemView.findViewById(R.id.welfare_image);
            }
        }
    }
}
