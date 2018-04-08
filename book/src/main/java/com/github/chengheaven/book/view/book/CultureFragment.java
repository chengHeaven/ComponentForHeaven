package com.github.chengheaven.book.view.book;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.book.R;
import com.github.chengheaven.book.bean.BookData;
import com.github.chengheaven.book.presenter.book.CultureContract;
import com.github.chengheaven.book.view.detail.BookDetailActivity;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/12.
 */

public class CultureFragment extends BaseFragment implements CultureContract.View {

    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    LinearLayout mErrorBar;
    XRecyclerView mBookRecycler;

    private AnimationDrawable mAnimationDrawable;

    private int start = 0;
    private int count = 30;
    private CultureAdapter mAdapter;

    private CultureContract.Presenter mPresenter;

    public static CultureFragment newInstance() {
        return new CultureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_sub_fragment, container, false);

        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mBookRecycler = view.findViewById(R.id.book_sub_fragment_recycler);

        showLoading();
        mBookRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new CultureAdapter();
        mBookRecycler.setAdapter(mAdapter);
        mBookRecycler.setPullRefreshEnabled(false);
        mBookRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                start = 0;
                mPresenter.getCultureList(view, getString(R.string.book_refresh), "文化", start, count);
            }

            @Override
            public void onLoadMore() {
                start += count;
                mPresenter.getCultureList(view, null, "文化", start, count);
            }
        });

        mPresenter.getCultureList(view, null, "文化", start, count);

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (CultureContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mBookRecycler.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshCultureView(List<BookData.BookBean> results) {
        mBookRecycler.refreshComplete();
        mAdapter.refreshList(results);
    }

    @Override
    public void updateCultureView(List<BookData.BookBean> results) {
        mBookRecycler.refreshComplete();
        mAdapter.updateList(results);
    }

    class CultureAdapter extends RecyclerView.Adapter<CultureAdapter.ViewHolder> {

        private List<BookData.BookBean> mList;

        void updateList(List<BookData.BookBean> list) {
            mList = new ArrayList<>();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        void refreshList(List<BookData.BookBean> list) {
            mList.clear();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mList.get(position).getImages().getLarge())
                    .asBitmap()
                    .placeholder(R.drawable.img_one)
                    .fitCenter()
                    .into(holder.mBookImage);
            holder.mBookName.setText(mList.get(position).getTitle());
            holder.mBookScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookBean", mList.get(position));
                intent.putExtra("item", bundle);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mBookImage;
            TextView mBookName;
            TextView mBookScore;

            ViewHolder(View itemView) {
                super(itemView);
                mBookImage = itemView.findViewById(R.id.book_item_image);
                mBookName = itemView.findViewById(R.id.book_item_name);
                mBookScore = itemView.findViewById(R.id.book_item_score);
            }
        }
    }
}