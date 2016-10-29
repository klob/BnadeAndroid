package com.klobliu.wow.utils;

/**
 * Created by klobliu on 2016/9/4.
 */

public class HTTPLog {
    private static boolean isDebug=false;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        HTTPLog.isDebug = isDebug;
    }
}
