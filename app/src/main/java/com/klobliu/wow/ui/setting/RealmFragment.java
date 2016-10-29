package com.klobliu.wow.ui.setting;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.klobliu.wow.BladeManager;
import com.klobliu.wow.fragment.BaseListFragment;
import com.klobliu.wow.model.Realm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by klobliu on 2016/9/4.
 */

public class RealmFragment extends BaseListFragment<Realm> {
    @Override
    public void onLoadMore() {

    }

    @Override
    public void initAdapter() {
        mAdapter = new RealmAdapter(getContext(), mListItems);
    }

    public abstract class DoubleClickListener implements View.OnClickListener {

        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
                onDoubleClick(v);
            } else {
                lastClickTime = currentTime;
            }
        }

        protected abstract void onDoubleClick(View v);
    }

    @Override
    public void initViews() {
        super.initViews();
        hideBar();
        getShowBar().setOnClickListener(new DoubleClickListener() {
            @Override
            protected void onDoubleClick(View v) {
                int id = Realm.getId("白银之手");
                BladeManager.getInstance().setRealmId(id);
                BladeManager.getInstance().putCache();
                getContext().finish();
            }
        });
    }

    @Override
    public void initDataList(boolean isUpdate) {
        List<Realm> realmList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : Realm.sRealmMap.entrySet()) {
            Realm realm = new Realm();
            realm.name = entry.getKey();
            realm.id = entry.getValue();
            realmList.add(realm);
        }
        callInitDataList(true, realmList);
        setLoadMorable(false);
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        Realm realm = mListItems.get(position);
        ShowToast("你选择了" + realm.name);
        BladeManager.getInstance().setRealmId(realm.id);
        BladeManager.getInstance().putCache();
        getContext().finish();
    }
}
