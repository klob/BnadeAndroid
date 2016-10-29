package com.klobliu.wow.network;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-16  .
 * *********    Time : 22:35 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class RDCallBackBase extends StringCallback {
    abstract Object parse(String response);

    ;

}
