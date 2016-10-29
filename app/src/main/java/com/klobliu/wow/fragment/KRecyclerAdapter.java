package com.klobliu.wow.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-01-04  .
 * *********    Time : 10:16 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class KRecyclerAdapter<T, VH extends KViewHolder> extends CommonRecyclerViewAdapter<T> {
    public KRecyclerAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder viewHolder, int position) {
        ((VH) viewHolder).bindData(getItem(position),position,this);
    }


}
