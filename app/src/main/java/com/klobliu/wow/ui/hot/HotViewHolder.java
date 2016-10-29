package com.klobliu.wow.ui.hot;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandi.klob.sdk.photo.ImageLoadTool;
import com.klobliu.wow.R;
import com.klobliu.wow.fragment.CommonRecyclerViewAdapter;
import com.klobliu.wow.fragment.KViewHolder;
import com.klobliu.wow.model.HotItem;

/**
 * Created by klobliu on 2016/8/28.
 */

public class HotViewHolder extends KViewHolder<HotItem> {
    TextView hotTxt;
    ImageView icon;
    TextView queried;

    public HotViewHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.item_hot_item_icon);
        hotTxt = (TextView) itemView.findViewById(R.id.txt_hot);
        queried = (TextView) itemView.findViewById(R.id.item_hot_txt_queried);
    }

    @Override
    public void bindData(HotItem data, int position, CommonRecyclerViewAdapter adapter) {
        hotTxt.setText(data.name);
        ImageLoadTool.getInstance().loadImage(icon, data.getIconUrl());
        queried.setText(String.format("搜索次数：%d", data.queried));
    }
}
