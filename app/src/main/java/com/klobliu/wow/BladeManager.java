package com.klobliu.wow;

import com.diandi.klob.sdk.common.Global;
import com.klobliu.wow.model.Realm;

/**
 * Created by klobliu on 2016/9/4.
 */

public class BladeManager {
    private static volatile BladeManager sInstance;
    int realmId = 136;

    public static BladeManager getInstance() {
        if (sInstance == null) {
            synchronized (BladeManager.class) {
                if (sInstance == null) {
                    sInstance = new BladeManager();
                }
            }
        }
        return sInstance;
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public void init() {
        realmId = getFromCache();
        BladeManager.getInstance().getCurrentRealmName();
    }

    public int getFromCache() {
        Integer i = (Integer) Global.getCache().getAsObject("realmId");
        if (i == null) {
            i = 136;
        }
        return i;
    }

    public void putCache() {
        Global.getCache().put("realmId", realmId);
    }

    public String getCurrentRealmName() {
        return Realm.getName(realmId);
    }
}
