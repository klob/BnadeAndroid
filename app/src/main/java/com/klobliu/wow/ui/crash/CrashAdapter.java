package com.klobliu.wow.ui.crash;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klobliu.sdk.app.crash.LocalCrash;
import com.klobliu.wow.R;
import com.klobliu.wow.fragment.CommonRecyclerViewAdapter;
import com.klobliu.wow.fragment.KRecyclerAdapter;
import com.klobliu.wow.fragment.KViewHolder;

import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-01-01  .
 * *********    Time : 09:14 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CrashAdapter extends KRecyclerAdapter<LocalCrash, CrashAdapter.ViewHolder> {
    public CrashAdapter(Context context, List<LocalCrash> list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_crash;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(getView(parent));
    }

    class ViewHolder extends KViewHolder<LocalCrash> {
        TextView primary;
        TextView secondary;
        TextView versionName;

        public ViewHolder(View itemView) {
            super(itemView);
            primary = (TextView) itemView.findViewById(R.id.primary);
            secondary = (TextView) itemView.findViewById(R.id.secondary);
            versionName = (TextView) itemView.findViewById(R.id.versionName);
        }

        @Override
        public void bindData(LocalCrash data, int position, CommonRecyclerViewAdapter adapter) {
            primary.setText(data.getTitle());
            secondary.setText(data.getSummary());
            versionName.setText(data.getVersionName());
        }


    }
}
