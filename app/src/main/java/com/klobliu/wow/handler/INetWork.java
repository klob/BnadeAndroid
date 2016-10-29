package com.klobliu.wow.handler;

/**
 * Created by klobliu on 2016/8/14.
 */
public interface INetWork<T> extends INetWorkBase {
    void onSuccess(T t, boolean fromCache);
}
