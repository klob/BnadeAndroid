package com.klobliu.wow.network;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-17  .
 * *********    Time : 23:39 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class RXFindListener<T> extends Callback<T> {
    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(T response, int id) {

    }
}
