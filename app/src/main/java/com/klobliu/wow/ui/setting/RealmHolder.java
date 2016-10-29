package com.klobliu.wow.ui.setting;

import android.view.View;
import android.widget.TextView;

import com.klobliu.wow.R;
import com.klobliu.wow.fragment.CommonRecyclerViewAdapter;
import com.klobliu.wow.fragment.KViewHolder;
import com.klobliu.wow.model.Realm;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by klobliu on 2016/9/4.
 */

public class RealmHolder extends KViewHolder<Realm> {
    @BindView(R.id.item_realm_name)
    TextView name;

    public RealmHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Realm data, int position, CommonRecyclerViewAdapter adapter) {
        name.setText(data.name);
    }
}
