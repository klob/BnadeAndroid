package com.klobliu.wow.model;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-27  .
 * *********    Time : 23:31 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class HotItem extends ItemBase {
    public int queried;

    @Override
    public String toString() {
        return "HotItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                "，queried=" + queried + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
