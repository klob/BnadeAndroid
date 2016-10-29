package com.klobliu.wow.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diandi.klob.sdk.cache.CacheDispatcher;
import com.diandi.klob.sdk.cache.CacheFactory;
import com.diandi.klob.sdk.cache.CacheTask;
import com.diandi.klob.sdk.common.WeakHandler;
import com.diandi.klob.sdk.processor.Processor;
import com.diandi.klob.sdk.processor.WorkHandler;
import com.diandi.klob.sdk.util.CollectionUtils;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.photo.ScreenUtils;
import com.klobliu.wow.R;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-25  .
 * *********    Time : 11:30 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class BaseListFragment<T> extends BaseFragment implements IBus<T>, SwipeRefreshLayout.OnRefreshListener, UltimateRecyclerView.OnLoadMoreListener, ItemTouchListenerAdapter.RecyclerViewOnItemClickListener {
    public final static int LIMIT_COUNT = 200;
    public final static int mSkip = 10;
    public final static int SUCCESS_UPDATE = 100;
    public final static int SUCCESS_NOT_UPDATE = 101;
    public final static int ERROR = 102;
    public final static int LOAD_MORE = 103;
    public final static int HIDE_TIPS = 104;
    public final static int LOAD_MORE_DISABLE = 105;
    private final static int TRUE_UPDATE = 201;
    private final static int TRUE_LOAD_MORE = 203;
    private final static int NOTIFY_LIST = 204;
    protected CommonRecyclerView mRecyclerView;
    protected CommonRecyclerViewAdapter<T> mAdapter;
    //泛型数据
    protected ArrayList<T> mListItems = new ArrayList<>();
    //泛型缓存
    protected List<T> mCacheList = new ArrayList<>();

    //页码
    protected int mPageNum;

    //缓存任务
    protected CacheTask mCacheTask;
    //缓存key
    protected String mCacheKey = null;
    //缓存发动机
    protected CacheDispatcher mCacheDispatcher;

    protected int mMaxCount = 200;
    //数据量
    protected int mCount = mMaxCount;
    protected int mScreenHeight = 0;
    protected WeakHandler mUiHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_UPDATE:
                    refreshProcess(mCacheList);
                case TRUE_UPDATE:
                    fresh(true, mCacheList);
                    break;
                case SUCCESS_NOT_UPDATE:
                    fresh(false, mCacheList);
                    break;
                case ERROR:
                    setLoadMorable(false);
                    refreshPull();
                    mPageNum = 0;
                    //ShowToast(R.string.network_tips);
                    break;
                case LOAD_MORE:
                    loadMoreProcess(mCacheList);
                    break;
                case TRUE_LOAD_MORE:
                    loadMore();
                    break;
                case LOAD_MORE_DISABLE:
                    setLoadMorable(false);
                    break;
                case NOTIFY_LIST:
                    mAdapter.notifyDataSetChanged();
                    break;
                case HIDE_TIPS:
                    break;
            }
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.v(TAG);
        View view = inflater.inflate(R.layout.fragment_base_recyclerview, container, false);
        mRecyclerView = (CommonRecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void initViews() {
    }

    @Override
    public void init() {

        mCacheDispatcher = CacheFactory.create();
        mPageNum = 0;
        // mQuery.addWhereLessThan("createdAt", new BmobDate(new Date(System.currentTimeMillis())));
        initCache();
    }

    //刷新调用的方法
    @Override
    public void onRefresh() {
        L.d(TAG, "onRefresh");
        mPageNum = 0;
        initDataList(true);
    }

    /**
     * do your work
     */

    public void processData(List<T> data) {

    }

    boolean mNeedProcess = false;

    public void refreshProcess(final List<T> data) {
        if (mNeedProcess) {
            Processor.execute(new WorkHandler() {
                @Override
                public void start() {
                    processData(data);
                }

                @Override
                public void over() {
                    mUiHandler.sendEmptyMessage(TRUE_UPDATE);
                }
            });
        } else {
            mUiHandler.sendEmptyMessage(TRUE_UPDATE);
        }

    }

    public void loadMoreProcess(final List<T> data) {
        if (mNeedProcess) {
            Processor.execute(new WorkHandler() {
                @Override
                public void start() {
                    processData(data);
                }

                @Override
                public void over() {
                    mUiHandler.sendEmptyMessage(TRUE_LOAD_MORE);
                }
            });
        } else {
            mUiHandler.sendEmptyMessage(TRUE_LOAD_MORE);
        }
    }

    protected void callLoadMore(List<T> list) {


        if (CollectionUtils.isNotNull(list)) {
            mCacheList = list;
            mUiHandler.sendEmptyMessage(TRUE_LOAD_MORE);
        } else {
            setLoadMorable(false);
        }
        final int rest = mCount - mListItems.size();
        setLoadMorable(needLoadMore(rest));
    }

    public boolean needLoadMore(int rest) {

        /*if (rest < 0) {
            count();
        } else*/
        boolean needLoadMore = true;
        if (rest < mSkip || mListItems.size() >= mMaxCount) {
            needLoadMore = false;
        } else {
            needLoadMore = true;
        }
        return needLoadMore;
    }

    @Override
    public void fresh(final boolean isUpdate, final List<T> list) {
   /*     mListItems.clear();
        mAdapter.notifyDataSetChanged();*/
        if (CollectionUtils.isNotNull(list)) {
            if (isUpdate) {
                mListItems.clear();
            }
            //  mAdapter.removeAll();
            mListItems.addAll(list);
            //count();
            //  L.e(TAG, mListItems);
            //   mAdapter.setList(mListItems);
            mUiHandler.sendEmptyMessageDelayed(NOTIFY_LIST, 200);
            setLoadMorable(needLoadMore(list.size()));

        } else {
            L.i("查询成功:无返回值");
            //  if (mListItems != null) {
            //      mListItems.clear();}

            setLoadMorable(false);
        }

        refreshPull();
        //这样能保证每次查询都是从头开始
        // mPageNum = 0;

    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        L.v(TAG);
        if (mRecyclerView.isLoadMoreEnabled()) {
            refreshPull();
            onLoadMore();
        }
    /*    L.d(TAG,"itemsCount         "+itemsCount+"         maxLastVisiblePosition        " +maxLastVisiblePosition+"");
        if (mCount == 0) {
            onLoadMore();
        } else if (mCount > mListItems.size()) {
            onLoadMore();
        } else {
            L.e(TAG,"setLoadMorable");
            setLoadMorable(false);
        }*/
    }

    @Override
    public void initListView() {
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setDefaultOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, R.color.divider));
        /*mRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            int mScrollY;

            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                mScrollY = scrollY;
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (getShowBar() != null && CollectionUtils.isNotNull(mListItems) && mListItems.size() > 4) {
                    if (observableScrollState == ObservableScrollState.UP) {
                        L.e(TAG, mScrollY);
                        if (mScrollY > 20) {
                            mRecyclerView.hideView(getShowBar(), mRecyclerView, getScreenHeight());
                        }   // mRecyclerView.hideFloatingActionMenu();
                    } else if (observableScrollState == ObservableScrollState.DOWN) {
                        mRecyclerView.showView(getShowBar(), mRecyclerView, getScreenHeight());
                        //  mRecyclerView.showFloatingActionMenu();
                    }
                }
            }
        });*/
        mRecyclerView.setEmptyView(R.layout.custom_bottom_progressbar, 0);
        mRecyclerView.setLoadMoreView(LayoutInflater.from(getContext()).inflate(R.layout.custom_bottom_progressbar, null));
        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(mRecyclerView.mRecyclerView, this);
        mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);
    }

    public int getScreenHeight() {
        if (mScreenHeight == 0) {
            int screenHeight = ScreenUtils.getScreenHeight();
            int statusBarHeight = getBarHeight();
            int barHeight = getResources().getDimensionPixelSize(R.dimen.base_action_bar_height);
            int topHeight = barHeight + statusBarHeight;
     /*   if (getContext() instanceof MainActivity) {
            currentWindowScreenHeight = screenHeight - topHeight - ((MainActivity) getContext()).getTabHeight();
        } else {
            currentWindowScreenHeight = screenHeight - topHeight;
        }*/
            mScreenHeight = screenHeight - topHeight;
        }
        return mScreenHeight;
    }

    //加载更多
    public void loadMore() {
        mListItems.addAll(mCacheList);
        mAdapter.notifyDataSetChanged();
        refreshLoad();
    }

    @Override
    public void setLoadMorable(boolean loadMorable) {
        if (loadMorable) {
            mRecyclerView.reenableLoadmore();
            L.v(TAG, "enableLoadmore");
        } else {
            mRecyclerView.disableLoadmore();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyItemChanged(mAdapter.getItemCount());
                }
            }, 500);
            L.v(TAG, "disableLoadmore");
        }
        refreshLoad();

    }

    //初始化缓存
    public void initCache() {
        if (mCacheKey != null) {
            mCacheTask = new CacheTask(TAG + KUserHelper.getUserId() + mCacheKey, this);
        } else {
            mCacheTask = new CacheTask(TAG + KUserHelper.getUserId(), this);
        }
        mCacheDispatcher.addGetTask(mCacheTask);
    }

    //保存缓存
    public void saveCache() {
        if (mAdapter != null) {
            if (mAdapter.getList() != null) {
                mListItems = (ArrayList) mAdapter.getList();
                if (mCacheKey != null) {
                    mCacheTask = new CacheTask(TAG + KUserHelper.getUserId() + mCacheKey, mListItems);
                } else {
                    mCacheTask = new CacheTask(TAG + KUserHelper.getUserId(), mListItems);
                }
                mCacheDispatcher.addPutTask(mCacheTask);
            }
        }
    }

    @Override
    public void setCacheKey(String key) {
        mCacheKey = key;
    }

    @Override
    public void refreshLoad() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void refreshPull() {
        mRecyclerView.setRefreshing(false);
    }

    //取得缓存成功
    @Override
    public void onSuccess(List object) {
        L.e(TAG, "onSuccess");
        mListItems = (ArrayList<T>) object;
        initAll();
        count();

    } //取得缓存失败

    @Override
    public void onFailure(boolean exist) {
        L.e(TAG, "onFailure");
        initAll();
    }

    public void initAll() {
        initAdapter();
        initListView();
        initCallBack();
        mRecyclerView.setAdapter(mAdapter);
        initDataList(true);
    }

    protected void initCallBack() {
    }


    //统计数据量
    public void count() {
        /*if (mListItems.size() == 0) {
            return;
        }
        final Class<?> clazz = mListItems.get(0).getClass();
        mCount = 200;*/

    }

    //此时保存缓存
    @Override
    public void onStop() {
        super.onStop();
        saveCache();
    }

    //此时移除Handler 防止内存泄露
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public abstract void onLoadMore(); /*{
        if (mCount != mListItems.size()) {
            mPageNum++;
           *//* mQuery.setSkip(KQuery.LIMIT_COUNT * (mPageNum));
            mQuery.findObjects(getActivity(), mLoadMoreFindListener);*//*
        } else {
            setLoadMorable(false);
        }
    }*/


    @Override
    public abstract void initDataList(final boolean isUpdate);

    protected void callInitDataList(boolean isUpdate, List<T> list) {
        mCacheList = list;
        if (isUpdate) {
            mUiHandler.sendEmptyMessage(TRUE_UPDATE);
        } else {
            mUiHandler.sendEmptyMessage(SUCCESS_NOT_UPDATE);
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {

    }

    @Override
    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

    }
}
