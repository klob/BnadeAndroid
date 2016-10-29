package com.klobliu.wow.model;

import com.j256.ormlite.table.DatabaseTable;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-15  .
 * *********    Time : 22:09 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class Item extends ItemBase{


    public String[] bonusList = null;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", bonusList=" + bonusList +
                '}';
    }
}
