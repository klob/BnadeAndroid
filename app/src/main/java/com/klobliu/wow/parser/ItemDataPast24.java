package com.klobliu.wow.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.klobliu.wow.model.ItemData;
import com.klobliu.wow.utils.HTTPLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-30  .
 * *********    Time : 23:33 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class ItemDataPast24 extends ParseBase<ItemData> {
    @Override
    public List<ItemData> parse(String response) {
        List<ItemData> items = new ArrayList<ItemData>();
        JSONArray array = JSON.parseArray(response);
        ItemData latestData = new ItemData();
        for (Object o : array) {
            JSONArray itemArray = JSON.parseArray(o.toString());
            ItemData itemData = new ItemData();
            itemData.price = itemArray.getLong(0);
            itemData.quality = itemArray.getShort(1);
            itemData.time = itemArray.getLong(2);
            if (itemData.time > latestData.time) {
                latestData = itemData;
            }
            items.add(itemData);
        }
        setExtra(latestData);
        Collections.sort(items, getComparator());
        // data = new ArrayList<>(items);
        if (HTTPLog.isDebug()) {
            print();
        }
        return items;
    }

    @Override
    public Comparator<ItemData> getComparator() {
        return new Comparator<ItemData>() {
            @Override
            public int compare(ItemData lhs, ItemData rhs) {
                if (lhs.price == rhs.price) {
                    return 0;
                }
                if (lhs.price > rhs.price) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
    }
}
