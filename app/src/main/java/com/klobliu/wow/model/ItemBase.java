package com.klobliu.wow.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.klobliu.wow.network.Blade;

import java.io.Serializable;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-27  .
 * *********    Time : 23:32 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
@DatabaseTable(tableName = "Item")
public class ItemBase implements Serializable {
    public final static String ITEM_BASE = "item_base";
    public final static String ITEM_ID = "item_id";
    public final static String ITEM_NAME = "item_name";
    @DatabaseField(useGetSet = true, generatedId = true)
    public int uid;
    @DatabaseField(useGetSet = true, uniqueIndex = true)
    public int id;
    @DatabaseField(useGetSet = true)
    public String name;
    @DatabaseField
    public String icon;
    @DatabaseField
    public boolean isLove;
    @DatabaseField(useGetSet = true)
    public String url;

    public ItemBase() {

    }

    @Override
    public String toString() {
        return "ItemBase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getIconUrl() {
        return Blade.getIcon(icon);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
