package com.github.chengheaven.technology.data.technology;

import com.github.chengheaven.technology.bean.GankData;
import com.github.chengheaven.technology.bean.HomeBean;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/25.
 *         数据请求接口
 */
public interface TechnologyDataSource {

    interface EveryCallback<T> {

        /**
         * banner请求成功
         *
         * @param results List<T>
         */
        void onSuccess(List<T> results);

        /**
         * 请求失败
         *
         * @param msg String
         */
        void onFailed(String msg);
    }

    interface EveryContentCallback<T> {

        /**
         * 首页数据请求成功
         *
         * @param results List<List<T>>
         */
        void onSuccess(List<List<T>> results);

        /**
         * 首页数据请求是吧
         *
         * @param msg String
         */
        void onFailed(String msg);
    }

    /**
     * Banner url
     *
     * @param callback callback
     */
    void getBanner(EveryCallback callback);

    /**
     * Home page url
     *
     * @param year     年
     * @param month    月
     * @param day      日
     * @param callback callback
     */
    void getRecycler(String year, String month, String day, EveryContentCallback<HomeBean> callback);

    /**
     * Android / iOS / Welfare url
     *
     * @param id       数据类型，Android / iOS / all / 前端 / 瞎推荐 / 休息视频 / 拓展资源 / 福利 / App
     * @param page     获取第几页
     * @param pre      获取多少条数据
     * @param callback callback
     */
    void getGankData(String id, int page, int pre, EveryCallback callback);

    /**
     * Get banner data from memory
     *
     * @return banner url
     */
    List<String> getImageUrlsLocal();

    /**
     * Get home page data from memory
     *
     * @return home page data
     */
    List<List<HomeBean>> getLocalHomeData();

    /**
     * Set welfare image list to memory
     *
     * @param list welfare image list
     */
    void setWelfareImageToLocal(List<String> list);

    /**
     * Get welfare image list from memory
     *
     * @return welfare image list
     */
    List<String> getWelfareImageFromLocal();

    /**
     * Set Customization list to memory
     *
     * @param list Customization list
     * @param add  refresh or load more
     */
    void setCustomizationToLocal(List<GankData.ResultsBean> list, boolean add);

    /**
     * Get Customization list from memory
     *
     * @return Customization list
     */
    List<GankData.ResultsBean> getCustomizationFromLocal();

    /**
     * Set Android list to memory
     *
     * @param list Android list
     * @param add  refresh or load more
     */
    void setAndroidToLocal(List<GankData.ResultsBean> list, boolean add);

    /**
     * Get Android list from memory
     *
     * @return Android list
     */
    List<GankData.ResultsBean> getAndroidFromLocal();

    /**
     * Clear the date in memory
     */
    void clear();
}
