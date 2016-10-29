package com.klobliu.wow.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diandi.klob.sdk.ui.common.KFragment;
import com.diandi.klob.sdk.widget.ActionCompat;

import java.lang.reflect.Field;

import butterknife.ButterKnife;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-06-22  .
 * *********    Time : 10:40 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * Fragment基础类
 */
public abstract class BaseFragment extends KFragment implements ActionCompat.ActionSheetListener {


    View mRootView;

    public BaseFragment() {
        super();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void init() {
        /*if (getShowBar() != null) {
            getShowBar().getBackgroundLayout().setBackgroundResource(R.drawable.bg_top_bar);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutView() != -1) {
            mRootView = inject(inflater, container, getLayoutView());
        }
        return mRootView;
    }

    protected
    @LayoutRes
    int getLayoutView() {
        return -1;
    }


    protected View inject(LayoutInflater inflater, ViewGroup container, int layoutId) {
        View view = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    public int getBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 54;//默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }



}
