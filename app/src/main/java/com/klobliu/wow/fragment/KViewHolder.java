package com.klobliu.wow.fragment;

import android.view.View;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-01-04  .
 * *********    Time : 10:19 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class KViewHolder<T> extends CommonViewHolder {
    public KViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data, int position, CommonRecyclerViewAdapter adapter);
}
