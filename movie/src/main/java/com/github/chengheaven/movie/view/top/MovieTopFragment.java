package com.github.chengheaven.movie.view.top;

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
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.presenter.top.MovieTopContract;
import com.github.chengheaven.movie.view.detail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieTopFragment extends BaseFragment implements MovieTopContract.View {

    private ImageView mLoadingImage;
    private LinearLayout mLoadingBar;
    private LinearLayout mErrorBar;
    private AnimationDrawable mAnimation;
    private XRecyclerView mRecycler;
    private MovieTopAdapter mAdapter;
    private int start = 0;
    private int count = 30;

    private MovieTopContract.Presenter mPresenter;

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_top_frag, container, false);

        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mRecycler = view.findViewById(R.id.book_top_fragment_recycler);

        showLoading();

        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new MovieTopAdapter();
        mRecycler.setPullRefreshEnabled(false);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                start = 0;
                mPresenter.getMovieTopData(view, getString(R.string.refreshing), start, count);
            }

            @Override
            public void onLoadMore() {
                start += count;
                mPresenter.getMovieTopData(view, null, start, count);
            }
        });

        mPresenter.getMovieTopData(view, null, start, count);

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MovieTopContract.Presenter) presenter;
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimation = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimation.start();
    }

    @Override
    public void hideLoading() {
        mAnimation.stop();
        mLoadingBar.setVisibility(View.GONE);
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
    public void update(List<MovieBean.SubjectsBean> list) {
        mRecycler.refreshComplete();
        mAdapter.update(list);
    }

    @Override
    public void refresh(List<MovieBean.SubjectsBean> list) {
        mRecycler.refreshComplete();
        mAdapter.refresh(list);
    }

    class MovieTopAdapter extends RecyclerView.Adapter<MovieTopAdapter.ViewHolder> {

        private List<MovieBean.SubjectsBean> mList = new ArrayList<>();

        void update(List<MovieBean.SubjectsBean> list) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }

        void refresh(List<MovieBean.SubjectsBean> list) {
            mList.clear();
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_book_fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mList.get(position).getImages().getLarge())
                    .asBitmap()
                    .placeholder(R.drawable.img_one)
                    .fitCenter()
                    .into(holder.mImage);

            holder.mName.setText(mList.get(position).getTitle());
            holder.mScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movieBean", mList.get(position));
                intent.putExtra("item", bundle);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImage;
            TextView mName;
            TextView mScore;

            ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.book_item_image);
                mName = itemView.findViewById(R.id.book_item_name);
                mScore = itemView.findViewById(R.id.book_item_score);
            }
        }
    }
}
