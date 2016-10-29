package com.klobliu.wow.ui.setting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.klobliu.wow.R;
import com.klobliu.wow.fragment.KRecyclerAdapter;
import com.klobliu.wow.model.Realm;

import java.util.List;

/**
 * Created by klobliu on 2016/9/4.
 */

public class RealmAdapter extends KRecyclerAdapter<Realm,RealmHolder> {
    public RealmAdapter(Context context, List<Realm> list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_realm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RealmHolder(getView(parent));
    }
}
