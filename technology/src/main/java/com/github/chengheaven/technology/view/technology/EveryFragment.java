package com.github.chengheaven.technology.view.technology;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chengheaven.componentservice.utils.GlideImageLoader;
import com.github.chengheaven.componentservice.utils.SharedPreferenceUtil;
import com.github.chengheaven.componentservice.utils.TimeUtil;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.bean.HomeBean;
import com.github.chengheaven.technology.constants.Constants;
import com.github.chengheaven.technology.presenter.technology.EveryContract;
import com.github.chengheaven.technology.view.webview.WebViewActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 */
public class EveryFragment extends BaseFragment implements EveryContract.View {

    ImageView mLoadingImage;
    LinearLayout mLoading;
    RecyclerView mEveryRecycler;
    private EveryContract.Presenter mPresenter;
    private EveryRecyclerAdapter mEveryRecyclerAdapter;
    private EveryAdapter mAdapter;
    private RotateAnimation mAnimation;
    private List<String> last = new ArrayList<>();
    private boolean isFirst = true;

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (EveryContract.Presenter) presenter;
    }

    public static EveryFragment newInstance() {
        return new EveryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_every_fragment, container, false);

        mLoadingImage = view.findViewById(R.id.loading_image);
        mLoading = view.findViewById(R.id.loading);
        mEveryRecycler = view.findViewById(R.id.every_recycler);
        mEveryRecycler.setVisibility(View.GONE);

        mEveryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mEveryRecyclerAdapter = new EveryRecyclerAdapter();
        mEveryRecycler.setAdapter(mEveryRecyclerAdapter);

        String time = TimeUtil.getData();
        String[] s = time.split("-");
        mLoading.setVisibility(View.VISIBLE);
        mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画持续时间
        mAnimation.setDuration(500);
        //不停顿
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(10000);
        mLoadingImage.setAnimation(mAnimation);
        if (isFirst) {
            isFirst = false;
            mPresenter.getBannerUrl();
            if (TimeUtil.isRightTime()) {
                mPresenter.getRecycler(s[0], s[1], s[2]);
                last = Arrays.asList(s);
            } else {
                last = TimeUtil.getLastTime(s[0], s[1], s[2]);
                mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
            }
//            mAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    mPresenter.getBannerUrl();
//                    if (TimeUtil.isRightTime()) {
//                        mPresenter.getRecycler(s[0], s[1], s[2]);
//                        last = Arrays.asList(s);
//                    } else {
//                        last = TimeUtil.getLastTime(s[0], s[1], s[2]);
//                        mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
//                    }
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
////                    hideAnimation();
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
        } else {
            mPresenter.getRecyclerAndBannerFromLocal();
        }

        mLoadingImage.setOnClickListener(v -> {
            showAnimation();
            mPresenter.getBannerUrl();
            mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void updateBannerUrl(List<String> urls) {
        mEveryRecyclerAdapter.updateUrl(urls);
    }

    @Override
    public void updateRecyclerAdapter(List<List<HomeBean>> lists) {
        mEveryRecycler.setVisibility(View.VISIBLE);
        mEveryRecyclerAdapter.updateList(lists);
    }

    @Override
    public void hideAnimation() {
        mLoading.setVisibility(View.GONE);
        mAnimation.cancel();
    }

    @Override
    public void stopAnimation() {
        mAnimation.cancel();
    }

    @Override
    public void showAnimation() {
        mLoading.setVisibility(View.VISIBLE);
        mLoadingImage.startAnimation(mAnimation);
    }

    @Override
    public void getAgainRecycler() {
        last = TimeUtil.getLastTime(last.get(0), last.get(1), last.get(2));
        mPresenter.getRecycler(last.get(0), last.get(1), last.get(2));
    }

    class EveryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<List<HomeBean>> mList;
        private List<String> mUrls = new ArrayList<>();
        private static final int HEADER = 1;
        private static final int RECYCLER = 2;
        private static final int BOTTOM = 3;

        void updateUrl(List<String> urls) {
            mUrls = urls;
            notifyDataSetChanged();
        }

        void updateList(List<List<HomeBean>> lists) {
            mList = new ArrayList<>();
            mList = lists;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return HEADER;
                case 1:
                    return RECYCLER;
                case 2:
                    return BOTTOM;
                default:
                    break;
            }
            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case HEADER:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_header, parent, false);
                    return new HeaderViewHolder(view);
                case RECYCLER:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler, parent, false);
                    return new RecyclerViewHolder(view);
                case BOTTOM:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_bottom, parent, false);
                    return new BottomViewHolder(view);
                default:
                    break;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder) {
                ((HeaderViewHolder) holder).mBanner.setImageLoader(new GlideImageLoader());
                ((HeaderViewHolder) holder).mBanner.setImages(mUrls);
                ((HeaderViewHolder) holder).mBanner.setDelayTime(3000);
                ((HeaderViewHolder) holder).mBanner.start();
                ((HeaderViewHolder) holder).mBanner.getFocusedChild();
            } else if (holder instanceof RecyclerViewHolder) {
                ((RecyclerViewHolder) holder).mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                ((RecyclerViewHolder) holder).mRecycler.setNestedScrollingEnabled(true);
                mAdapter = new EveryAdapter(mList);
                ((RecyclerViewHolder) holder).mRecycler.setAdapter(mAdapter);
            } else if (holder instanceof BottomViewHolder) {
                ((BottomViewHolder) holder).mChangeItem.setVisibility(View.VISIBLE);
                ((BottomViewHolder) holder).mChangeItem.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), ItemChangeActivity.class);
                    startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            Banner mBanner;

            HeaderViewHolder(View itemView) {
                super(itemView);
                mBanner = itemView.findViewById(R.id.code_home_every_banner);
                itemView.setOnClickListener(v -> {
                    int i = v.getId();
                    if (i == R.id.code_home_every_xd) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", "https://gank.io/xiandu");
                        intent.putExtra("title", getActivity().getResources().getString(R.string.loading));
                        getActivity().startActivity(intent);

                    } else if (i == R.id.code_home_daily_btn) {
//                        RxPosition msgPosition = new RxPosition();
//                        msgPosition.setPosition(2);
//                        RxBus.getDefault().post(msgPosition);
//                        RxCustomer msg = new RxCustomer();
//                        msg.setType(getString(R.string.all));
//                        RxBus.getDefault().post(msg);

                    } else if (i == R.id.code_home_every_hot) {
//                        RxDaily rxDaily = new RxDaily();
//                        rxDaily.setPosition(1);
//                        RxBus.getDefault().post(rxDaily);

                    }
                });
            }
        }

        class RecyclerViewHolder extends RecyclerView.ViewHolder {

            RecyclerView mRecycler;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                mRecycler = itemView.findViewById(R.id.code_home_every_recycler);
            }
        }

        class BottomViewHolder extends RecyclerView.ViewHolder {

            LinearLayout mChangeItem;

            BottomViewHolder(View itemView) {
                super(itemView);
                mChangeItem = itemView.findViewById(R.id.code_home_change_item);
            }
        }
    }

    class EveryAdapter extends RecyclerView.Adapter<EveryAdapter.ViewHolder> {

        private List<List<HomeBean>> mList;
        private List<String> mItemList = Arrays.asList(SharedPreferenceUtil.getInstance(getContext()).getItemPosition().split(" "));
        private RecyclerItemAdapter mAdapter;
        private boolean isHave = false;
        private final int MAX_ITEM = 6;

        EveryAdapter(List<List<HomeBean>> list) {
            mList = new ArrayList<>();
            mList = list;
            notifyDataSetChanged();
        }

        void update(List<String> list) {
            mItemList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_every_recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            isHave = false;
            for (int i = 0; i < mList.size(); i++) {
                if (mItemList.get(position).equals(Constants.getTitles().get(mList.get(i).get(0).getType()))) {
                    isHave = true;
                    holder.setVisibility(true);
                    holder.mTitleText.setText(mItemList.get(position));
                    holder.mTitleImage.setImageResource(mList.get(i).get(0).getDrawable());

                    holder.mItemRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 6));
                    holder.mItemRecycler.setNestedScrollingEnabled(false);

                    if (mList.get(i).size() > MAX_ITEM) {
                        List<HomeBean> list = new ArrayList<>();
                        for (int j = 0; j < MAX_ITEM; j++) {
                            list.add(mList.get(i).get(j));
                        }
                        mAdapter = new RecyclerItemAdapter(list);
                    } else {
                        mAdapter = new RecyclerItemAdapter(mList.get(i));
                    }
                    holder.mItemRecycler.setAdapter(mAdapter);
                }
            }

            if (!isHave) {
                holder.setVisibility(false);
            }
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mItemList == null ? 0 : mItemList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mTitleImage;
            TextView mTitleText;
            TextView mMore;
            ImageView mTitleImageMore;
            RecyclerView mItemRecycler;

            ViewHolder(View itemView) {
                super(itemView);
                mTitleImage = itemView.findViewById(R.id.code_title_image);
                mTitleText = itemView.findViewById(R.id.code_title_text);
                mMore = itemView.findViewById(R.id.code_tv_more);
                mTitleImageMore = itemView.findViewById(R.id.code_title_image_more);
                mItemRecycler = itemView.findViewById(R.id.code_item_recycler);
            }

            public void setVisibility(boolean visible) {
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                if (visible) {
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    itemView.setVisibility(View.VISIBLE);
                } else {
                    itemView.setVisibility(View.GONE);
                    param.height = 0;
                    param.width = 0;
                }
                itemView.setLayoutParams(param);
            }
        }
    }

    class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder> {

        private List<HomeBean> mList;

        RecyclerItemAdapter(List<HomeBean> list) {
            mList = new ArrayList<>();
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mList.get(position).getType().contains(getString(R.string.code_welfare))) {
                Glide.with(getActivity())
                        .load(mList.get(position).getUrl())
                        .placeholder(R.drawable.img_two_bi_one)
                        .crossFade(1500)
                        .centerCrop()
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mImage);
            } else {

                Glide.with(getActivity())
//                        .load(mList.get(position).getUrl())
                        .load(mList.get(position).getImage())
                        .placeholder(R.drawable.img_two_bi_one)
                        .crossFade(1500)
//                        .centerCrop()
                        .error(R.drawable.img_two_bi_one)
                        .into(holder.mImage);
            }
            holder.mText.setText(mList.get(position).getDesc());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", mList.get(position).getUrl());
                intent.putExtra("title", getActivity().getResources().getString(R.string.loading));
                getActivity().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : (mList.size() > 6 ? 6 : mList.size());
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (mList.size() == 1) {
                        return 6;
                    } else if (mList.size() == 2) {
                        return 3;
                    } else if (mList.size() % 3 == 0) {
                        return 2;
                    } else if (mList.size() == 4) {
                        switch (position) {
                            case 3:
                                return 6;
                            default:
                                return 2;
                        }
                    } else if (mList.size() == 5) {
                        switch (position) {
                            case 3:
                                return 3;
                            case 4:
                                return 3;
                            default:
                                return 2;
                        }
                    }
                    return 0;
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView mImage;
            TextView mText;

            ViewHolder(View itemView) {
                super(itemView);
                mImage = itemView.findViewById(R.id.code_recycler_item_image);
                mText = itemView.findViewById(R.id.code_recycler_item_text);
            }
        }
    }
}
