package com.klobliu.wow.ui.crash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.widget.DialogUtils;
import com.diandi.klob.sdk.widget.TopBar;
import com.klobliu.sdk.app.crash.CrashDao;
import com.klobliu.sdk.app.crash.LocalCrash;
import com.klobliu.wow.R;
import com.klobliu.wow.fragment.BaseListFragment;
import com.klobliu.wow.ui.BaseActivity;

import java.util.List;

/**
 * Created by klobliu on 2016/8/29.
 */

public class CrashFragment extends BaseListFragment<LocalCrash> {
    @Override
    public void onLoadMore() {
        setLoadMorable(false);
    }

    @Override
    public void initViews() {
        super.initViews();
        initTopBarForBoth("崩溃日志", R.drawable.top_bar_delete_btn, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                DialogUtils.showSelectDialog(mContext, "删除所有", new DialogUtils.ConfirmListener() {
                    @Override
                    public void onClick() {
                        List<LocalCrash> crashs = mAdapter.getList();
                        for (LocalCrash crash : crashs) {
                            CrashDao.getInstance(mContext).delete(crash);
                        }
                        mAdapter.removeAll();
                    }
                });
            }
        });
        showBar();
    }

    @Override
    public void initAdapter() {
        mAdapter = new CrashAdapter(getContext(), mListItems);
    }

    @Override
    public void initDataList(boolean isUpdate) {
        List<LocalCrash> crashList = CrashDao.getInstance(getContext()).queryAll();
        callInitDataList(true, crashList);
        L.e(TAG, crashList);
        setLoadMorable(false);
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        super.onItemClick(parent, clickedView, position);
        final LocalCrash crash = mAdapter.getItem(position);
        Intent intent = new Intent(mContext, CrashDetailActivity.class);
        intent.putExtra(LocalCrash.LOCAL_CRASH, crash);
        startActivityForResult(intent, 101);
        in();
    }


    public static class CrashActivity extends BaseActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pushFragmentToStack(CrashFragment.class, null);
            hideBar();
        }
    }
}
