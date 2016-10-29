package com.klobliu.wow.data;

/**
 * Created by klobliu on 2016/8/14.
 */
public interface RDCallBack<T> {
    void onResult(T t, boolean isError);
}
