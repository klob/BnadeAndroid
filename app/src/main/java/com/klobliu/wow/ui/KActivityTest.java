package com.klobliu.wow.ui;

import android.os.Bundle;

import com.diandi.klob.sdk.ui.common.KTabActivity;
import com.diandi.klob.sdk.ui.common.KTabItem;
import com.klobliu.wow.ui.love.LoveFragment;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-09-16  .
 * *********    Time : 14:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class KActivityTest extends KTabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabManager.addTab(new KTabItem(LoveFragment.class, com.diandi.klob.sdk.R.drawable.top_bar_add_btn, "Love"));
        mTabManager.addTab(new KTabItem(LoveFragment.class, com.diandi.klob.sdk.R.drawable.top_bar_add_btn, "Love"));
        mTabManager.addTab(new KTabItem(LoveFragment.class, com.diandi.klob.sdk.R.drawable.top_bar_add_btn, "Love"));
        mTabManager.addTab(new KTabItem(LoveFragment.class, com.diandi.klob.sdk.R.drawable.top_bar_add_btn, "Love"));
        mTabManager.build();
    }

    @Override
    protected int getTabCount() {
        return 4;
    }
}
