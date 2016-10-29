package com.klobliu.wow.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.photo.ImageLoadTool;
import com.klobliu.wow.BuildConfig;
import com.klobliu.wow.MainActivity;
import com.klobliu.wow.R;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Project name : Diandi1.18 .
 * *********    Version : 1.0
 * *********    Copyright @ 2014, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * Splash页
 */
public class SplashActivity extends BaseActivity {


    TextView mVersionText;
    ImageView mBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setClearContentView(R.layout.activity_splash);
        //TCAgent.init(this,"4E02D810937C52B7130CC8293E239E48","main");
    /*    new Thread() {
            @Override
            public void run() {
                if (Controller.IS_SENSETIVE_FILETER) {
                    KeywordFilter.getInstance().init(XApplication.getInstance());
                    KeywordFilter.main();
                }
            }
        }.start();*/


        //ImageSize imageSize = new ImageSize(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
        mBg = (ImageView) findViewById(R.id.splash_bg);
        mBg.setImageBitmap(ImageLoadTool.getImageLoader().loadImageSync("drawable://" + R.drawable.splash));
        mVersionText = (TextView) findViewById(R.id.version_name);
        mVersionText.setText("v " + BuildConfig.VERSION_NAME);
        setSwipeBackEnable(false);
        SimpleTask.postDelay(new Runnable() {
            @Override
            public void run() {
                startAnimActivity(MainActivity.class);
                finish();
            }
        }, 2000);
    }


}
