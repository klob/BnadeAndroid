package com.klobliu.wow.network;

import com.alibaba.fastjson.JSON;
import com.diandi.klob.sdk.okhttp.callback.ResultCallback;
import com.diandi.klob.sdk.processor.Processor;
import com.diandi.klob.sdk.processor.WorkHandler;
import com.diandi.klob.sdk.util.L;
import com.klobliu.wow.data.IParse;
import com.klobliu.wow.data.RDCallBack;

import java.util.List;

import okhttp3.Call;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-15  .
 * *********    Time : 22:22 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class RDFindListener<T> extends RDCallBackBase implements RDCallBack<List<T>> {

    private static final String TAG = "RDGetListener";
    private IParse<T> mParser;
    private boolean mIsError = false;

    @Override
    public void onError(Call call, Exception e, int id) {
        onResult(null, true);
    }

    public IParse<T> getParser() {
        return mParser;
    }

    public IParse<T> createParser() {
        return null;
    }

    @Override
    Object parse(String response) {
        List<T> ts = null;
        mParser = createParser();
        if (mParser == null) {
            try {
                ts = (List<T>) JSON.parseArray(response, ResultCallback.getSuperclassTypeParameters2(getClass()));
            } catch (Exception e) {
                mIsError = true;
                L.w(TAG, response);
                e.printStackTrace();
            }
        } else {
            ts = mParser.parse(response);
        }
        return ts;
    }

    @Override
    public void onResponse(final String response, int id) {
        Processor.execute(new WorkHandler() {
            List<T> ts;

            @Override
            public void start() {
                ts = (List<T>) parse(response);
            }

            @Override
            public void over() {
                onResult(ts, mIsError);
            }
        });

    }


}
