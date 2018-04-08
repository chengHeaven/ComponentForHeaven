package com.github.chengheaven.movie.view.detail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.componentservice.customer.XRecyclerView.XRecyclerView;
import com.github.chengheaven.componentservice.customer.statusbar.StatusBarUtil;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.componentservice.view.webview.WebViewActivity;
import com.github.chengheaven.movie.R;
import com.github.chengheaven.movie.bean.MovieBean;
import com.github.chengheaven.movie.bean.MovieDetailBean;
import com.github.chengheaven.movie.presenter.detail.MovieDetailContract;
import com.github.chengheaven.movie.util.ResourceUtil;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {

    ImageView mBackground;
    ImageView mImage;
    TextView mScore;
    TextView mScorePeople;
    TextView mDirectors;
    TextView mPerformer;
    TextView mGenres;
    TextView mDay;
    TextView mCity;

    TextView mAnotherTitle;
    TextView mDetail;
    XRecyclerView mPerformerRecycler;
    LinearLayout mFragmentContent;

    LinearLayout mErrorBar;
    ImageView mLoadingImage;
    LinearLayout mLoadingBar;
    private AnimationDrawable mAnimationDrawable;

    private int slidingDistance;
    private int imageBgHeight;

    private MovieDetailAdapter mAdapter;

    private MovieDetailContract.Presenter mPresenter;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_frag, container, false);

        mBackground = view.findViewById(R.id.movie_detail_background);
        mImage = view.findViewById(R.id.movie_detail_image);
        mScore = view.findViewById(R.id.movie_detail_score);
        mScorePeople = view.findViewById(R.id.movie_detail_score_people);
        mDirectors = view.findViewById(R.id.movie_detail_directors);
        mPerformer = view.findViewById(R.id.movie_detail_performer);
        mGenres = view.findViewById(R.id.movie_detail_genres);
        mDay = view.findViewById(R.id.movie_detail_day);
        mCity = view.findViewById(R.id.movie_detail_city);

        mAnotherTitle = view.findViewById(R.id.movie_detail_another_title);
        mDetail = view.findViewById(R.id.movie_detail_detail);
        mPerformerRecycler = view.findViewById(R.id.movie_detail_performer_recycler);
        mFragmentContent = view.findViewById(R.id.movie_detail_fragment_content);

        mErrorBar = view.findViewById(R.id.ll_error_refresh);
        mLoadingImage = view.findViewById(R.id.image_loading);
        mLoadingBar = view.findViewById(R.id.ll_loading_bar);

        showLoading();

        mPerformerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPerformerRecycler.setPullRefreshEnabled(false);
        mPerformerRecycler.setLoadingMoreEnabled(false);
        mPerformerRecycler.setNestedScrollingEnabled(false);
        mAdapter = new MovieDetailAdapter();
        mPerformerRecycler.setAdapter(mAdapter);

        mPresenter.start();
        mPresenter.getMovieDetailData(view);

        mErrorBar.setOnClickListener(v -> {
            hideError();
            showLoading();
            mPresenter.getMovieDetailData(view);
        });

        return view;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (MovieDetailContract.Presenter) presenter;
    }

    @Override
    public void update(MovieDetailBean result) {
        StringBuilder sCity = new StringBuilder();
        for (String city : result.getCountries()) {
            sCity.append(city).append(" / ");
        }
        mCity.setText(String.format("制片国家/地区：%s", "".equals(sCity.toString()) ? "" : sCity.substring(0, sCity.length() - 3)));
        StringBuilder sAnotherTitle = new StringBuilder();
        for (String genres : result.getAka()) {
            sAnotherTitle.append(genres).append(" / ");
        }
        mAnotherTitle.setText("".equals(sAnotherTitle.toString()) ? "无"
                : sAnotherTitle.substring(0, sAnotherTitle.length() - 3));
        mDetail.setText(result.getSummary());

        mAdapter.update(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void initSlideShapeTheme(ImageView mHeaderBg) {

        // toolbar 的高
        int toolbarHeight = ((MovieDetailActivity) getActivity()).getToolbar().getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(getActivity());

        // 使背景图向上移动到图片的最低端，保留（TitleBar + StatusBar）的高度
        ViewGroup.LayoutParams params = ((MovieDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) ((MovieDetailActivity) getActivity()).getBackgroundImage().getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        ((MovieDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
        StatusBarUtil.setTranslucentImageHeader(getActivity(), 0, ((MovieDetailActivity) getActivity()).getToolbar());

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(getActivity()), 0, 0);

            ViewGroup.LayoutParams imgItemBgParams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgParams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }

    private void initScrollViewListener() {
        // 为了兼容23以下
        ((MovieDetailActivity) getActivity()).getBaseScroll().setOnScrollChangeListener((scrollX, scrollY, oldScrollX, oldScrollY) -> scrollChangeHeader(scrollY));
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (ResourceUtil.getDimens(R.dimen.movie_nav_bar_height) + StatusBarUtil.getStatusBarHeight(getActivity()));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (ResourceUtil.getDimens(R.dimen.movie_base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = ((MovieDetailActivity) getActivity()).getBackgroundImage().getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            ((MovieDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            ((MovieDetailActivity) getActivity()).getBackgroundImage().setImageDrawable(drawable);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateTitleView(MovieBean.SubjectsBean result) {
        initSlideShapeTheme(mBackground);
        StringBuilder sb = new StringBuilder();
        sb.append("主演：");
        for (MovieBean.SubjectsBean.CastsBean performer : result.getCasts()) {
            sb.append(performer.getName()).append(" / ");
        }
        ((MovieDetailActivity) getActivity()).setTitle(result.getTitle(), "".equals(sb.toString()) ? "" : sb.substring(0, sb.length() - 3));

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((MovieDetailActivity) getActivity()).setToolbarBackground(Color.TRANSPARENT);
                ((MovieDetailActivity) getActivity()).getBackgroundImage().setImageAlpha(0);
                ((MovieDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                return false;
            }
        }).into(((MovieDetailActivity) getActivity()).getBackgroundImage());

        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this)
                .load(result.getImages().getMedium())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(getActivity().getApplicationContext(), 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((MovieDetailActivity) getActivity()).getBackgroundImage().setVisibility(View.VISIBLE);
                mBackground.setImageAlpha(255);
                mBackground.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(mBackground);

        Glide.with(getActivity())
                .load(result.getImages().getLarge())
                .into(mImage);

        mScore.setText(String.format("评分：%s", result.getRating().getAverage()));
        mScorePeople.setText(String.format("%s 人评分", result.getCollect_count()));
        StringBuilder sDirector = new StringBuilder();
        for (MovieBean.SubjectsBean.DirectorsBean director : result.getDirectors()) {
            sDirector.append(director.getName()).append(" / ");
        }
        mDirectors.setText("".equals(sDirector.toString()) ? "" : sDirector.substring(0, sDirector.length() - 3));

        StringBuilder sPerformer = new StringBuilder();
        for (MovieBean.SubjectsBean.CastsBean cast : result.getCasts()) {
            sPerformer.append(cast.getName()).append(" / ");
        }
        mPerformer.setText("".equals(sPerformer.toString()) ? "" : sPerformer.substring(0, sPerformer.length() - 3));
        StringBuilder sType = new StringBuilder();
        for (String genres : result.getGenres()) {
            sType.append(genres).append(" / ");
        }
        mGenres.setText(String.format("类型：%s", "".equals(sType.toString()) ? "" : sType.substring(0, sType.length() - 3)));
        mDay.setText(String.format("上映日期：%s", result.getYear()));
    }

    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mAnimationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void hideLoading() {
        mFragmentContent.setVisibility(View.VISIBLE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    @Override
    public void showError() {
        mFragmentContent.setVisibility(View.GONE);
        mLoadingBar.setVisibility(View.GONE);
        mAnimationDrawable.stop();
        mErrorBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorBar.setVisibility(View.GONE);
    }

    class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {

        private MovieDetailBean mData;

        void update(MovieDetailBean result) {
            mData = result;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_fragment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!(mData.getDirectors().size() - 1 < position)) {
                Glide.with(getActivity())
                        .load(mData.getDirectors().get(position).getAvatars().getLarge())
                        .placeholder(R.drawable.img_one)
                        .centerCrop()
                        .into(holder.mImage);
                holder.mName.setText(mData.getDirectors().get(position).getName());
                holder.mDir.setText("导演");
            } else {
                Glide.with(getActivity())
                        .load(mData.getCasts().get(position - mData.getDirectors().size()).getAvatars().getLarge())
                        .placeholder(R.drawable.img_one)
                        .centerCrop()
                        .into(holder.mImage);
                holder.mName.setText(mData.getCasts().get(position - mData.getDirectors().size()).getName());
                holder.mDir.setText("演员");
            }

            if (position == (mData.getDirectors().size() + mData.getCasts().size() - 1)) {
                holder.mDivider.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", !(mData.getDirectors().size() - 1 < position)
                        ? mData.getDirectors().get(position).getName()
                        : mData.getCasts().get(position - mData.getDirectors().size()).getName());
                intent.putExtra("url", !(mData.getDirectors().size() - 1 < position)
                        ? mData.getDirectors().get(position).getAlt()
                        : mData.getCasts().get(position - mData.getDirectors().size()).getAlt());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : (mData.getDirectors().size() + mData.getCasts().size());
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImage;
            TextView mName;
            TextView mDir;
            View mDivider;

            ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.movie_detail_item_image);
                mName = itemView.findViewById(R.id.movie_detail_item_name);
                mDir = itemView.findViewById(R.id.movie_detail_item_dir);
                mDivider = itemView.findViewById(R.id.movie_detail_divider);
            }
        }
    }
}
