package com.klobliu.wow.handler;

import java.util.List;

/**
 * Created by klobliu on 2016/8/14.
 */
public interface IListNetWork<T> extends INetWorkBase {
    void onSuccess(List<T> data, boolean fromCache);
}
