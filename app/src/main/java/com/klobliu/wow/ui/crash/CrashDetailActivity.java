package com.klobliu.wow.ui.crash;

import android.os.Bundle;
import android.widget.TextView;

import com.diandi.klob.sdk.support.inject.InjectUtility;
import com.diandi.klob.sdk.support.inject.ViewInject;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.widget.TopBar;
import com.klobliu.sdk.app.crash.CrashDao;
import com.klobliu.sdk.app.crash.LocalCrash;
import com.klobliu.wow.R;
import com.klobliu.wow.ui.BaseActivity;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-01-03  .
 * *********    Time : 21:14 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CrashDetailActivity extends BaseActivity {

    TextView primary;
    TextView secondary;
    TextView versionName;
    @ViewInject(idStr = "info")
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        InjectUtility.initInjectedView(this, getView());
        final LocalCrash crash = (LocalCrash) getIntent().getExtras().getSerializable(LocalCrash.LOCAL_CRASH);

        initTopBarForBoth("崩溃日志详情", R.drawable.top_bar_delete_btn, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                assert crash != null;
                CrashDao.getInstance(mContext).delete(crash);
            }
        });
        versionName = (TextView) findViewById(R.id.versionName);
        primary = (TextView) findViewById(R.id.primary);
        secondary = (TextView) findViewById(R.id.secondary);
        primary.setText(crash.getTitle());
        secondary.setText(crash.getSummary());
        secondary.setMaxLines(100);
        versionName.setText(crash.getVersionName());
        info.setText(crash.getInfo());
        L.e("Crash", crash.getSummary());


    }

    @Override
    public void initViews() {

    }


    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }
}
