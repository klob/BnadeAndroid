package com.klobliu.wow.ui.love;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.klobliu.wow.R;
import com.klobliu.wow.db.ItemDao;
import com.klobliu.wow.fragment.RDFragment;
import com.klobliu.wow.model.Item;
import com.klobliu.wow.model.ItemBase;
import com.klobliu.wow.ui.BaseActivity;
import com.klobliu.wow.ui.ItemDataActivity;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-27  .
 * *********    Time : 23:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class LoveFragment extends RDFragment<ItemBase> {
    @Override
    public void initViews() {
        super.initViews();
        hideBar();
    }

    @Override
    protected void initCallBack() {

    }

    @Override
    public void onLoadMore() {
        /*mBlade.loadTopItems(new RDFindListener<HotItem>() {
            @Override
            public void onResult(List<HotItem> hotItems, boolean isError) {
                if (!isError) {
                    callLoadMore(hotItems);
                }
                L.d(TAG, hotItems);
            }
        });*/
        setLoadMorable(false);
    }

    @Override
    public void initDataList(boolean isUpdate) {
        callInitDataList(true,ItemDao.getInstance().queryLoved());
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        super.onItemClick(parent, clickedView, position);
        startAnimActivity(ItemDataActivity.class, Item.ITEM_BASE, mListItems.get(position));
    }

    @Override
    public void initAdapter() {
        mAdapter = new LoveAdapter(getContext(), mListItems);
    }

    public static class LoveActivity extends BaseActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pushFragmentToStack(LoveFragment.class, null, getString(R.string.love_top_bar));
            //   initTopBarForLeft();
        }
    }
}
