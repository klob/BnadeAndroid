package com.klobliu.wow.ui;

import com.diandi.klob.sdk.ui.common.KActivity;

/**
 * Created by klobliu on 2016/8/28.
 */

public class BaseActivity extends KActivity {
    @Override
    public void initViews() {

    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }

    public void startActivity(int page) {
        startAnimActivity(RouterActivity.class, "type", page);
    }
}
