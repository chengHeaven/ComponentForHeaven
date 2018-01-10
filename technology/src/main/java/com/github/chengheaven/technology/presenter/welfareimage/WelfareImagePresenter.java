package com.github.chengheaven.technology.presenter.welfareimage;

import com.github.chengheaven.technology.data.DataRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven_Cheng Created on 2018/1/8.
 */
public class WelfareImagePresenter implements WelfareImageContract.Presenter {

    private final WelfareImageContract.View mView;
    private final DataRepository mDataRepository;

    private int mPosition = 0;

    @Inject
    WelfareImagePresenter(WelfareImageContract.View view, DataRepository dataRepository) {
        this.mView = view;
        this.mDataRepository = dataRepository;
    }


    @Inject
    @Override
    public void setupPresenterToView() {
        //noinspection unchecked
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    @Override
    public void initViewPagerData() {
        mView.updateViewPager(getWelfare());
        mView.setPageCount(getWelfare().size());
        mView.setCurrentItemViewPager(mPosition);
    }

    private List<String> getWelfare() {
        return deepCopy(mDataRepository.getWelfareImageFromLocal());
    }

    private <T> List<T> deepCopy(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            //noinspection unchecked
            dest = (List<T>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }
}
