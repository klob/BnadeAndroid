package com.klobliu.wow.fragment;

import com.diandi.klob.sdk.cache.CacheTask;

import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-25  .
 * *********    Time : 11:39 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public interface IBus<T> extends CacheTask.GetCacheListener {

    //初始化列表
    void initListView();

    //取得缓存成功
    @Override
    void onSuccess(List object);

    //取得缓存失败
    @Override
    void onFailure(boolean exist);

    //初始化适配器
    void initAdapter();

    //UI刷新调用
    void onRefresh();

    //下拉刷新获取数据
    void initDataList(final boolean isFresh);

    //加载更多获取数据
    void onLoadMore();

    //刷新数据
    void fresh(final boolean isUpdate, final List<T> list);

    //加载更多
    void loadMore();

    //设置是否可以加载更多
    void setLoadMorable(boolean loadMorable);

    //预处理数据
    void processData(List<T> list);

     //刷新预处理
    void refreshProcess(final List<T> list);

    //加载更多预测处理
     void loadMoreProcess(final List<T> list);

    //初始化缓存
    void initCache();

    //保存缓存
    void saveCache();

    //停止刷新
    void refreshPull();

    //停止加载更多
    void refreshLoad();

    //设置缓存key
    void setCacheKey(String key);

    //统计数据量
    void count();

}
