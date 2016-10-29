package com.klobliu.wow.parser;

import com.diandi.klob.sdk.util.L;
import com.klobliu.wow.data.IParse;

import java.util.Comparator;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-30  .
 * *********    Time : 23:30 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public abstract class ParseBase<T> implements IParse<T> {
    protected final String TAG = getClass().getSimpleName();
    protected List<T> data;
    protected T extra;



    public void print() {
        L.w(TAG, data);
    }

    public List<T> getData() {
        return data;
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }

    public abstract Comparator<T> getComparator();

}
