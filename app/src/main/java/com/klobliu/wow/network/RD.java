package com.klobliu.wow.network;

import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.util.L;
import com.klobliu.wow.utils.HTTPLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by klobliu on 2016/8/14.
 */
public class RD {

    private static final String TAG = "RD";

    //http://www.bnade.com/wow/item/name/%E5%86%9B%E5%9B%A2%E5%8B%8B%E7%AB%A0
    public static void test(String url) {
        get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                L.e(TAG, response);
            }
        });
    }

    public static void test(String url, StringCallback callback) {
        get(url, callback);
    }

    public static void get(String url, final RDGetListener callback) {
        L.v(TAG, url);
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    public static void getWithCache(String url, final RDGetListener callback) {
        L.v(TAG, url);
        String string = Global.getCache().getAsString(url);
        if (string != null && callback != null) {
            L.d(TAG, string);
            Object o = callback.parse(string);
            L.d(TAG, o);
            callback.onResult(o, false);
        } else {
            getFromCache(url, callback);
        }
    }

    public static void get(final String url, final StringCallback callback) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.e(TAG, "url  " + url + "   " + e);
                if (callback != null) {
                    callback.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                //Global.getCache().put(url, response);
                if (HTTPLog.isDebug()) {
                    L.d(TAG, url);
                }
                if (HTTPLog.isDebug()) {
                    L.d(TAG, response);
                }
                if (callback != null) {
                    callback.onResponse(response, id);
                }
            }
        });
    }

    public static void getFromCache(final String url, final StringCallback callback) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.e(TAG, e);
                if (callback != null) {
                    callback.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Global.getCache().put(url, response);
                //L.d(TAG, response);
                if (callback != null) {
                    callback.onResponse(response, id);
                }
            }
        });
    }

}
