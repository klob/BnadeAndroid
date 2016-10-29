package com.klobliu.wow.model;

import com.diandi.klob.sdk.util.L;
import com.j256.ormlite.table.DatabaseTable;
import com.klobliu.wow.utils.TimeUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-16  .
 * *********    Time : 22:38 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class ItemData implements Serializable {

    public long price;
    public short quality;
    public long time;

    public String getPrice() {
        if (price > 100000) {
            return price / 10000 + "金";
        } else {
            return price / 10000 + "金" + String.format("%02d银", (price % 10000) / 100);
        }
    }

    public String getTime() {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.DEFAULT_FORMAT, Locale.US);
        return sdf.format(date);
    }

    public String getPriceAndTime() {
        return "价格:" + getPrice() + "   时间:" + getTime();
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "price=" + getPrice() +
                ", quality=" + quality +
                ", time=" + getTime() +
                '}';
    }
}
