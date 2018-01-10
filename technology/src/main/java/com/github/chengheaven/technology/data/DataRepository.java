package com.github.chengheaven.technology.data;

import com.github.chengheaven.technology.bean.HomeBean;
import com.github.chengheaven.technology.data.technology.TechnologyDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Heaven・Cheng Created on 2017/12/26.
 */
public class DataRepository implements TechnologyDataSource {

    private final TechnologyDataSource mTechnologyDataSource;

    @Inject
    DataRepository(TechnologyDataSource technologyDataSource) {
        this.mTechnologyDataSource = technologyDataSource;
    }

    @Override
    public void getBanner(EveryCallback callback) {
        mTechnologyDataSource.getBanner(callback);
    }

    @Override
    public void getRecycler(String year, String month, String day, EveryContentCallback<HomeBean> callback) {
        mTechnologyDataSource.getRecycler(year, month, day, callback);
    }

    @Override
    public void getGankData(String id, int page, int pre, EveryCallback callback) {
        mTechnologyDataSource.getGankData(id, page, pre, callback);
    }

    @Override
    public List<String> getImageUrlsLocal() {
        return mTechnologyDataSource.getImageUrlsLocal();
    }

    @Override
    public List<List<HomeBean>> getLocalHomeData() {
        return mTechnologyDataSource.getLocalHomeData();
    }

    @Override
    public void setWelfareImageToLocal(List<String> list) {
        mTechnologyDataSource.setWelfareImageToLocal(list);
    }

    @Override
    public List<String> getWelfareImageFromLocal() {
        return mTechnologyDataSource.getWelfareImageFromLocal();
    }

    @Override
    public void clear() {
        mTechnologyDataSource.clear();
    }
}
