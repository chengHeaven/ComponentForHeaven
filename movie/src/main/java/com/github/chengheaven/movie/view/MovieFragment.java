package com.github.chengheaven.movie.view;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.presenter.MovieContract;
import com.github.chengheaven.movie.view.detail.MovieDetailActivity;
import com.github.chengheaven.movie.view.top.MovieTopActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/27.
 */
public class MovieFragment extends BaseFragment implements MovieContract.View {

    private XRecyclerView mMovieRecycler;

    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    LinearLayout mErrorBar;
    private AnimationDrawable mAnimation;
    HeaderView mHeaderView;
    View mHeader;

    private MovieAdapter mAdapter;

    private MovieContract.Presenter mPresenter;

    public MovieFragment() {

    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_frag, container, false);

        mMovieRecycler = view.findViewById(R.id.movie_frag_recycler);
        mHeader = View.inflate(getContext(), R.layout.movie_header_layout, null);

        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);
        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        showLoading();

        mMovieRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMovieRecycler.setNestedScrollingEnabled(false);
        mMovieRecycler.setPullRefreshEnabled(false);
        mMovieRecycler.setLoadingMoreEnabled(false);
        mHeaderView = new HeaderView(mHeader);
        mMovieRecycler.addHeaderView(mHeader);
        mAdapter = new MovieAdapter();
        mMovieRecycler.setAdapter(mAdapter);

        mPresenter.getMovieData(view);

        mErrorBar.setOnClickListener(v -> {
            hideError();
            showLoading();
            mPresenter.getMovieData(view);
        });


        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MovieContract.Presenter) presenter;
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
        mAnimation.stop();
        mLoadingBar.setVisibility(View.GONE);
        mHeader.setVisibility(View.VISIBLE);
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

    private class HeaderView {

        RelativeLayout mTop;
        LinearLayout mHeader;

        HeaderView(View header) {
            mHeader = header.findViewById(R.id.movie_header);
            mTop = header.findViewById(R.id.movie_top);

            mTop.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), MovieTopActivity.class));
            });
        }
    }

    @Override
    public void update(List<MovieBean.SubjectsBean> list) {
        mAdapter.update(list);
    }

    class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

        private List<MovieBean.SubjectsBean> mList = new ArrayList<>();

        void update(List<MovieBean.SubjectsBean> list) {
            mList.clear();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_frag_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(getContext())
                    .load(mList.get(position).getImages().getLarge())
                    .asBitmap()
                    .fitCenter()
                    .into(holder.mImage);
            holder.mTitle.setText(mList.get(position).getTitle());
            StringBuilder directors = new StringBuilder();
            for (MovieBean.SubjectsBean.DirectorsBean person : mList.get(position).getDirectors()) {
                directors.append(person.getName()).append(", ");
            }
            holder.mDirector.setText("".equals(directors.toString()) ? "" : directors.substring(0, directors.length() - 2));
            StringBuilder casts = new StringBuilder();
            for (MovieBean.SubjectsBean.CastsBean person : mList.get(position).getCasts()) {
                casts.append(person.getName()).append(" / ");
            }
            holder.mPerformer.setText("".equals(casts.toString()) ? "" : casts.substring(0, casts.length() - 3));
            StringBuilder genres = new StringBuilder();
            for (String s : mList.get(position).getGenres()) {
                genres.append(s).append(" / ");
            }
            holder.mGenre.setText(String.format("类型：%s", "".equals(genres.toString()) ? "" : genres.substring(0, genres.length() - 3)));
            holder.mScore.setText(String.format("评分：%s", mList.get(position).getRating().getAverage()));

            if (position != mList.size() - 1) {
                holder.mDivider.setBackgroundResource(R.color.colorLineItem);
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movieBean", mList.get(position));
                intent.putExtra("item", bundle);
                startActivity(intent);
            });

            ViewHelper.setScaleX(holder.itemView, 0.8f);
            ViewHelper.setScaleY(holder.itemView, 0.8f);
            ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImage;
            TextView mTitle;
            TextView mDirector;
            TextView mPerformer;
            TextView mGenre;
            TextView mScore;
            View mDivider;

            ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.movie_item_image);
                mTitle = itemView.findViewById(R.id.movie_item_title);
                mDirector = itemView.findViewById(R.id.movie_item_director);
                mPerformer = itemView.findViewById(R.id.movie_item_performer);
                mGenre = itemView.findViewById(R.id.movie_item_genre);
                mScore = itemView.findViewById(R.id.movie_item_score);
                mDivider = itemView.findViewById(R.id.movie_divider_color);
            }
        }
    }
}
