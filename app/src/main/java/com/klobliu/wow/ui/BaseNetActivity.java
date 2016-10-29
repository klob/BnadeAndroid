package com.klobliu.wow.ui;

import com.diandi.klob.sdk.ui.common.KActivity;
import com.klobliu.sdk.app.data.IParse;
import com.klobliu.sdk.app.handler.INetWork;

/**
 * Created by klobliu on 2016/8/14.
 */
public class BaseNetActivity extends KActivity implements INetWork<Object>,IParse<Object> {


    @Override
    public void initViews() {

    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void onSuccess(Object o, boolean fromCache) {

    }

    @Override
    public String getRequestUrl() {
        return null;
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void parse(Object o, boolean isError) {

    }
}
