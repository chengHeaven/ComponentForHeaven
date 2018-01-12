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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.presenter.technology.CustomerContract;
import com.github.chengheaven.technology.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class CustomerFragment extends BaseFragment implements CustomerContract.View {

    private XRecyclerView mCustomizationRecycler;

    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    LinearLayout mErrorBar;
    private AnimationDrawable mAnimation;

    private int page = 1;
    private int per = 20;
    private String type = "all";
    HeaderView mHeaderView;
    View mHeader;
    private CustomizationAdapter mAdapter;

    private CustomerContract.Presenter mPresenter;
    private boolean mFlag = false;

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (CustomerContract.Presenter) presenter;
    }

    public static CustomerFragment newInstance() {
        return new CustomerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.technology_customer_fragment, container, false);

        mCustomizationRecycler = view.findViewById(R.id.customization_recycler);
        mHeader = View.inflate(getContext(), R.layout.technology_header_customization, null);

        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        showLoading();

        mCustomizationRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCustomizationRecycler.setPullRefreshEnabled(false);
        mHeaderView = new HeaderView(mHeader);
        mCustomizationRecycler.addHeaderView(mHeader);
        mAdapter = new CustomizationAdapter();
        mCustomizationRecycler.setAdapter(mAdapter);

        mPresenter.start();
        mPresenter.getCustomizationData(view, null, type, page, per);

        mCustomizationRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getCustomizationData(view, getString(R.string.technology_refresh), type, page, per);
            }
        });

        mErrorBar.setOnClickListener(v -> {
            hideError();
            showLoading();
            mPresenter.getCustomizationData(view, null, getString(R.string.technology_android), page, 20);
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mFlag) {
            mFlag = false;
            showLoading();
            Observable.timer(3000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(v -> hideLoading());
        }
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mHeader.setVisibility(View.GONE);
        mAnimation = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimation.start();
    }

    @Override
    public void hideLoading() {
        mLoadingBar.setVisibility(View.GONE);
        mHeader.setVisibility(View.VISIBLE);
        mAnimation.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mHeader.setVisibility(View.GONE);
        mAnimation.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @Override
    public void loadMoreData(List<GankData.ResultsBean> list) {
        mCustomizationRecycler.refreshComplete();
        mAdapter.add(list);
    }

    @Override
    public void loadAnotherTypeData(List<GankData.ResultsBean> list) {
        mCustomizationRecycler.refreshComplete();
        mAdapter.update(list);
    }

    @Override
    public void setType(String t) {
        type = t;
        mAdapter.clear();
        if (t.equals(getString(R.string.technology_all))) {
            mHeaderView.mTypeText.setText(getString(R.string.technology_all_chinese));
        } else {
            mHeaderView.mTypeText.setText(t);
        }
        if (t.equals(getString(R.string.technology_ios))) {
            type = getString(R.string.technology_iOS);
        }
        mPresenter.getCustomizationData(getView(), null, type, page, per);
    }

    @Override
    public void isIntent() {
        this.mFlag = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    private class HeaderView {

        LinearLayout mChooseBtn;
        TextView mTypeText;

        HeaderView(View header) {
            mChooseBtn = header.findViewById(R.id.ll_choose_catalogue);
            mTypeText = header.findViewById(R.id.customization_header_type_text);

            mChooseBtn.setOnClickListener(v -> new BottomSheet.Builder(getActivity(), R.style.Technology_BottomSheet_StyleDialog)
                    .title("选择分类")
                    .sheet(R.menu.technology_customization_menu)
                    .listener((dialog, which) -> {
                        if (which == R.id.gank_all) {
                            if (isOtherType(getString(R.string.technology_all_chinese))) {
                                mTypeText.setText(getString(R.string.technology_all_chinese));
                                type = getString(R.string.technology_all);
                            }
                        } else if (which == R.id.gank_android) {
                            if (isOtherType(getString(R.string.technology_android))) {
                                mTypeText.setText(R.string.technology_android);
                                type = getString(R.string.technology_android);
                            }
                        } else if (which == R.id.gank_ios) {
                            if (isOtherType(getString(R.string.technology_ios))) {
                                mTypeText.setText(R.string.technology_ios);
                                type = getString(R.string.technology_iOS);
                            }
                        } else if (which == R.id.gank_qian) {
                            if (isOtherType(getString(R.string.technology_front))) {
                                mTypeText.setText(R.string.technology_front);
                                type = getString(R.string.technology_front);
                            }
                        } else if (which == R.id.gank_xia) {
                            if (isOtherType(getString(R.string.technology_recommend))) {
                                mTypeText.setText(R.string.technology_recommend);
                                type = getString(R.string.technology_recommend);
                            }
                        } else if (which == R.id.gank_app) {
                            if (isOtherType(getString(R.string.technology_app))) {
                                mTypeText.setText(R.string.technology_app);
                                type = getString(R.string.technology_app);
                            }
                        } else if (which == R.id.gank_movie) {
                            if (isOtherType(getString(R.string.technology_video))) {
                                mTypeText.setText(R.string.technology_video);
                                type = getString(R.string.technology_video);
                            }
                        } else if (which == R.id.gank_resource) {
                            if (isOtherType(getString(R.string.technology_expand))) {
                                mTypeText.setText(R.string.technology_expand);
                                type = getString(R.string.technology_expand);
                            }
                        }
                        page = 1;
                        mPresenter.getCustomizationData(header, null, type, page, per);
                    }).show());

        }

        private boolean isOtherType(String selectType) {
            String clickText = mTypeText.getText().toString().trim();
            if (clickText.endsWith(selectType)) {
                toastMessage("当前已经是" + selectType + "分类");
                return false;
            } else {
                // 重置XRecyclerView状态，解决 如出现刷新到底无内容再切换其他类别后，无法上拉加载的情况
                mCustomizationRecycler.reset();
                return true;
            }
        }
    }

    class CustomizationAdapter extends RecyclerView.Adapter<CustomizationAdapter.ViewHolder> {

        private List<GankData.ResultsBean> mList = new ArrayList<>();

        void add(List<GankData.ResultsBean> list) {
            if (list != null && list.size() != 0) {
                mList.addAll(list);
                notifyDataSetChanged();
            }
        }

        void update(List<GankData.ResultsBean> list) {
            mList.clear();
            mList = list;
            notifyDataSetChanged();
        }

        void clear() {
            mList.clear();
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technology_customer_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mList.get(position).getType().equals(getString(R.string.technology_welfare_chinese))) {
                holder.mWelfare.setVisibility(View.VISIBLE);
                holder.mNormal.setVisibility(View.GONE);
                Glide.with(getContext())
                        .load(mList.get(position).getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mWelfare);
            } else {
                holder.mNormal.setVisibility(View.VISIBLE);
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
        }

        private String getDate(String date) {
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
            RelativeLayout mNormal;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.customization_title);
                mImage = itemView.findViewById(R.id.customization_image);
                mWelfare = itemView.findViewById(R.id.customization_welfare);
                mWho = itemView.findViewById(R.id.customization_who);
                mType = itemView.findViewById(R.id.customization_type);
                mDate = itemView.findViewById(R.id.customization_date);
                mNormal = itemView.findViewById(R.id.customization_normal);
            }
        }
    }
}
