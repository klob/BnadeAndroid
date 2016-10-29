package com.klobliu.wow.ui;

import android.os.Bundle;
import android.view.View;

import com.diandi.klob.sdk.widget.TopBar;
import com.klobliu.wow.R;

/**
 * Created by klobliu on 2016/9/16.
 */

public class UITestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_test);
        initTopBarForBoth("haha", R.drawable.bg_go_settings, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {

            }
        });
        setSwipeBackEnable(false);
    }

    public void onClick(View view) {
    }
}
