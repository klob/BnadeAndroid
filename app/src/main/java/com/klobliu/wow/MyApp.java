package com.klobliu.wow;

import android.app.Application;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.debug.DebugTime;
import com.diandi.klob.sdk.photo.ImageLoadTool;
import com.klobliu.sdk.app.bmob.BmobManager;
import com.klobliu.sdk.app.crash.CrashHandler;
import com.klobliu.sdk.app.crash.CrashStrategy;
import com.klobliu.sdk.app.crash.LocalCrash;
import com.klobliu.sdk.app.db.DBController;
import com.klobliu.wow.model.ItemBase;
import com.klobliu.wow.network.Blade;
import com.klobliu.wow.network.RD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-15  .
 * *********    Time : 23:08 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        XApplication.init(this, BuildConfig.DEBUG, true);

        DBController.getInstance().addModule(LocalCrash.class).addModule(ItemBase.class).init(6);

        BmobManager.getInstance().init();

        CrashStrategy strategy = new CrashStrategy();
        strategy.needShowLog = BuildConfig.DEBUG;
        strategy.needPushBmob = !BuildConfig.DEBUG;

        CrashHandler.register(this, strategy);

        ImageLoadTool.setsDefaultLoadingId(R.drawable.loading);
        BladeManager.getInstance().init();
    }
}
