package com.klobliu.wow.ui;

import android.os.Bundle;

import com.klobliu.wow.R;
import com.klobliu.wow.ui.love.LoveFragment;
import com.klobliu.wow.ui.setting.RealmFragment;

/**
 * Created by klobliu on 2016/9/4.
 */

public class RouterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                pushFragmentToStack(LoveFragment.class, null, getString(R.string.love_top_bar));
                break;
            case 1:
                pushFragmentToStack(LoveFragment.class, null, getString(R.string.love_top_bar));
                break;
            case 2:
                pushFragmentToStack(RealmFragment.class, null, getString(R.string.realm_top_bar));
                break;
        }
    }
}
