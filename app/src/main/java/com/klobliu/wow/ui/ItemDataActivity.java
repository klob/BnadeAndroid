package com.klobliu.wow.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.ui.common.KActivity;
import com.diandi.klob.sdk.util.CollectionUtils;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.widget.TopBar;
import com.klobliu.wow.R;
import com.klobliu.wow.data.IParse;
import com.klobliu.wow.db.ItemDao;
import com.klobliu.wow.model.Item;
import com.klobliu.wow.model.ItemBase;
import com.klobliu.wow.model.ItemData;
import com.klobliu.wow.network.Blade;
import com.klobliu.wow.network.RDGetListener;
import com.klobliu.wow.network.RDParserListener;
import com.klobliu.wow.parser.ItemDataPast24;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-17  .
 * *********    Time : 00:17 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ItemDataActivity extends KActivity {

    private TextView low;
    private TextView high;
    private TextView weeklow;
    private TextView weekhigh;
    private TextView recent;
    private TextView detail;
    Blade mBlade = new Blade();
    int mItemId;
    ItemBase mItem;
    @BindView(R.id.like)
    CheckBox like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_data);
        ButterKnife.bind(this);
        initViews();


        Intent intent = getIntent();
        ItemBase itemBase = (ItemBase) intent.getSerializableExtra(ItemBase.ITEM_BASE);
        if (itemBase != null) {
            queryByItem(itemBase);
            initByName(itemBase.name);
        } else {
            final String itemName = getIntent().getStringExtra(Item.ITEM_NAME);
            initByName(itemName);
            mBlade.getItemByName(itemName, new RDGetListener<ItemBase>() {
                @Override
                public void onResult(ItemBase item, boolean isError) {
                    if (isError) {
                        return;
                    }
                    queryByItem(item);
                }
            });

        }
    }


    public void initByName(final String itemName) {
        initTopBarForBoth(itemName, R.drawable.icon_open_in_browser_2, new TopBar.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                String url = "http://www.bnade.com/itemQuery.html?itemName=" + Blade.encode(itemName) + "&realm=" + Blade.encode("白银之手");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startAnimActivity(intent);
            }
        });
    }

    @Override
    public void initViews() {
        detail = (TextView) findViewById(R.id.detail);
        weeklow = (TextView) findViewById(R.id.week_low);
        weekhigh = (TextView) findViewById(R.id.week_high);
        low = (TextView) findViewById(R.id.low);
        high = (TextView) findViewById(R.id.high);
        recent = (TextView) findViewById(R.id.recent);
    }

    public void queryByName() {

    }

    public void queryByItem( ItemBase item) {
        mItem = ItemDao.getInstance().queryOrCreate(item);
        L.d(TAG,mItem.isLove);
        like.setChecked(mItem.isLove());
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItem.setLove(isChecked);
                ItemDao.getInstance().update(mItem);
            }
        });
        L.d(TAG, "id " + item.id);
        mBlade.loadItemDetail(item.id, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                detail.setText(Html.fromHtml(response.trim().replace("\n", "<br>").replace("&nbsp", " ")));
            }
        });
        mBlade.getPast24ById(item.id, new RDParserListener<ItemData>() {
            @Override
            public void onResult(List<ItemData> itemDatas, boolean isError) {
                if (isError || CollectionUtils.isNull(itemDatas)) {
                    Global.showMsg("未找到物品");
                    return;
                }
                recent.setText("最近" + ((ItemDataPast24) getParser()).getExtra().getPriceAndTime());
                low.setText("最低" + itemDatas.get(0).getPriceAndTime());
                high.setText(String.format("最高%s", itemDatas.get(itemDatas.size() - 1).getPriceAndTime()));
            }

            @Override
            public IParse<ItemData> createParser() {
                return new ItemDataPast24();
            }
        });
        mBlade.getHistoryById(item.id, new RDParserListener<ItemData>() {
            @Override
            public void onResult(List<ItemData> itemDatas, boolean isError) {
                if (isError || CollectionUtils.isNull(itemDatas)) {
                    Global.showMsg("解析出错");
                    return;
                }
                weeklow.setText("最低" + itemDatas.get(0).getPriceAndTime());
                weekhigh.setText(String.format("最高%s", itemDatas.get(itemDatas.size() - 1).getPriceAndTime()));
            }

            @Override
            public IParse<ItemData> createParser() {
                return new ItemDataPast24();
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }


}
