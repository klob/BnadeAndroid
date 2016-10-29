package com.klobliu.wow;

import android.os.Bundle;

import com.diandi.klob.sdk.ui.common.KTabActivity;

/**
 * Created by klob3 on 2016/10/29.
 */

public class MyTabActivity extends KTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mTabManager.addFragment()
    }

    @Override
    protected int getTabCount() {
        return 4;
    }
}
