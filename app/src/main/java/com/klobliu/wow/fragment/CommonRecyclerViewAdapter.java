package com.klobliu.wow.fragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diandi.klob.sdk.util.L;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-25  .
 * *********    Time : 08:42 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class CommonRecyclerViewAdapter<T> extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    protected final String TAG = getClass().getSimpleName();
    protected List<T> mDataList;
    protected Context mContext;

    public CommonRecyclerViewAdapter(Context context, List<T> list) {
        this.mDataList = list;
        this.mContext = context;
    }


    public void remove(T e) {
        int position = mDataList.indexOf(e);
        L.e(TAG, position);
        remove(position);
    }

    /**
     * notify  item
     *
     * @param object object T
     */
    public void notifyItemChanged(T object) {
        int position = 0;
        if (customHeaderView != null) position++;
        position = position + mDataList.indexOf(object);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        mDataList.remove(position);
        notifyDataSetChanged();
    }


    public void removeAll() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (customHeaderView != null)
            position--;
        return mDataList.get(position);
    }

    public List<T> getList() {
        return mDataList;
    }

    public void setList(List<T> list) {
        // mDataList=new ArrayList<>(list);
        //  notifyDataSetChanged();
    }

    //实际ItemCount
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    //数据ItemCount
    @Override
    public int getAdapterItemCount() {
        return mDataList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isBind(position)) {
            bindData(viewHolder, position);
        }
    }


    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    protected abstract void bindData(RecyclerView.ViewHolder viewHolder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public boolean isBind(int position) {
        return position < getItemCount() && (customHeaderView != null ? position <= mDataList.size() : position < mDataList.size()) && (customHeaderView != null ? position > 0 : true);
    }

    protected abstract
    @LayoutRes
    int getLayout();

    protected View getView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);

    }


}
