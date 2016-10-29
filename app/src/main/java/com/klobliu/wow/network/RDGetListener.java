package com.klobliu.wow.network;

import com.alibaba.fastjson.JSON;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.util.L;
import com.klobliu.wow.data.RDCallBack;
import com.klobliu.wow.utils.HTTPLog;

import okhttp3.Call;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-15  .
 * *********    Time : 22:19 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class RDGetListener<T> extends RDCallBackBase implements RDCallBack<T> {

    private static final String TAG = "RDGetListener";

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    Object parse(String response) {
        return JSON.parseArray(response, ResultCallback.getSuperclassTypeParameters2(getClass())).get(0);
    }

    @Override
    public void onResponse(String response, int id) {
        if (HTTPLog.isDebug()) {
            L.d(TAG, response);
        }
        if (response.equals("[]")) {
            onResult(null, true);
        } else {
            onResult((T) parse(response), false);
        }
    }

    public abstract void onResult(T t, boolean isError);


}
