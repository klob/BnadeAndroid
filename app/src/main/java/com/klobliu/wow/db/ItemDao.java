package com.klobliu.wow.db;

import android.content.Context;

import com.diandi.klob.sdk.XApplication;
import com.j256.ormlite.dao.Dao;
import com.klobliu.sdk.app.db.BaseDao;
import com.klobliu.wow.model.ItemBase;

import java.sql.SQLException;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-09-03  .
 * *********    Time : 11:48 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public class ItemDao extends BaseDao<ItemBase> {
    private static ItemDao mInstance;

    public ItemDao(Context context) {
        super(context);
    }

    public static ItemDao getInstance() {
        if (mInstance == null) {
            synchronized (ItemDao.class) {
                if (mInstance == null) {
                    mInstance = new ItemDao(XApplication.getInstance());
                }
            }
        }
        return mInstance;
    }

    @Override
    protected Dao<ItemBase, Integer> getDao() {
        if (mDao == null) {
            try {
                mDao = getHelper().getDao(ItemBase.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mDao;
    }

    public ItemBase queryByItemBase(ItemBase base) {
        return queryByParam("id", base.getId() + "");
    }

    public ItemBase queryOrCreate(ItemBase base) {
        ItemBase item = queryByItemBase(base);
        if (item != null) {
            return item;
        } else {
            create(base);
            return base;
        }
    }

    public List<ItemBase> queryLoved() {
       return query("isLove",true);
    }
}
