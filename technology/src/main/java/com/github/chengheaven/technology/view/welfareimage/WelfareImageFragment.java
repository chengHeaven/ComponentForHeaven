package com.github.chengheaven.technology.view.welfareimage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chengheaven.componentservice.utils.Utils;
import com.github.chengheaven.componentservice.view.BaseFragment;
import com.github.chengheaven.componentservice.view.BasePresenter;
import com.github.chengheaven.technology.R;
import com.github.chengheaven.technology.presenter.welfareimage.WelfareImageContract;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Heaven_Cheng Created on 2018/1/8.
 */
public class WelfareImageFragment extends BaseFragment implements WelfareImageContract.View, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private LinearLayout mIndicator;
    private TextView mSave;
    private int mIndicatorNum;

    WelfareViewPagerAdapter mViewPagerAdapter;

    private WelfareImageContract.Presenter mPresenter;

    public WelfareImageFragment() {

    }

    public static WelfareImageFragment newInstance() {
        return new WelfareImageFragment();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = (WelfareImageContract.Presenter) presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPagerAdapter = new WelfareViewPagerAdapter(getActivity(), new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_welfare_image_fragment, container, false);

        mViewPager = view.findViewById(R.id.welfare_image_fragment_viewpager);
        mIndicator = view.findViewById(R.id.welfare_image_viewpager_indicators);
        mSave = view.findViewById(R.id.welfare_image_save);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mSave.setOnClickListener(v -> {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            Observable.create(e -> {
                int position = mViewPager.getCurrentItem();
                String path = getImagePath(mViewPagerAdapter.mUrls.get(position));
                if (path != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                    if (bitmap != null) {
                        saveImageToGallery(bitmap);
                        Utils.showSnackBar(getActivity(), "已保存至"
                                + Environment.getExternalStorageDirectory().getAbsolutePath() + "/ComponentForHeaven");
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
        });

        mPresenter.initViewPagerData();

        return view;
    }

    private String getImagePath(String url) {
        try {
            FutureTarget<File> future = Glide.with(getActivity()).load(url).downloadOnly(500, 500);
            File cacheFile = future.get();
            return cacheFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveImageToGallery(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "ComponentForHeaven");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpeg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getAbsoluteFile())));
    }

    @Override
    public void updateViewPager(List<String> urls) {
        mViewPagerAdapter.update(urls);
    }

    @Override
    public void setCurrentItemViewPager(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setPageCount(int count) {
        mIndicatorNum = count;
        mIndicator.removeAllViews();
        final TextView indicator = getTextView();
        String indicatorText = "1" + " / " + count;
        SpannableStringBuilder builder = getSpannableStringBuilder(indicatorText);
        indicator.setText(builder);
        mIndicator.addView(indicator);
    }

    private TextView getTextView() {
        final TextView indicator = new TextView(getActivity());
        indicator.setTextSize(13);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(18, 0, 18, 10);
        indicator.setLayoutParams(params);
        return indicator;
    }

    private SpannableStringBuilder getSpannableStringBuilder(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.indicator));
        ForegroundColorSpan graySpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.indicator_text));
        int index = text.indexOf("/");
        builder.setSpan(blueSpan, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(graySpan, index, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public void updateIndicator(int position) {
        mIndicator.removeAllViews();
        final TextView indicator = getTextView();
        if (position > 2) {
            position = position % mIndicatorNum;
        }
        String indicatorText = (position + 1) + " / " + mIndicatorNum;
        SpannableStringBuilder builder = getSpannableStringBuilder(indicatorText);
        indicator.setText(builder);
        mIndicator.addView(indicator);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPagerAdapter.clear();
    }

    class WelfareViewPagerAdapter extends PagerAdapter {

        private ImageView mImage;
        private List<String> mUrls;
        private Activity mContext;

        WelfareViewPagerAdapter(Activity mContext, List<String> mUrls) {
            this.mContext = mContext;
            this.mUrls = mUrls;
        }

        void update(List<String> list) {
            mUrls.addAll(list);
            notifyDataSetChanged();
        }

        void clear() {
            mUrls.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mUrls == null ? 0 : mUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.code_welfare_image_item, null);
            mImage = view.findViewById(R.id.welfare_item_image);
            String path = mUrls.get(position);
            Glide.with(getActivity())
                    .load(path)
                    .crossFade(700)
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.img_two_bi_one)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            toastMessage("资源加载异常");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            int height = mImage.getHeight();
                            Point size = new Point();
                            getActivity().getWindowManager().getDefaultDisplay().getSize(size);
                            int h = size.y;
                            if (height > h) {
                                mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                mImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    }).into(mImage);
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View imageView = (View) object;
            if (imageView == null) {
                return;
            }
            Glide.clear(imageView);
            container.removeView(imageView);
        }
    }
}
