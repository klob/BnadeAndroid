package com.klobliu.wow.ui.hot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.klobliu.wow.R;
import com.klobliu.wow.fragment.KRecyclerAdapter;
import com.klobliu.wow.model.HotItem;

import java.util.List;

/**
 * Created by klobliu on 2016/8/28.
 */

public class HotAdapter extends KRecyclerAdapter<HotItem, HotViewHolder> {

    public HotAdapter(Context context, List<HotItem> list) {
        super(context, list);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_hot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HotViewHolder(getView(parent));
    }
}
