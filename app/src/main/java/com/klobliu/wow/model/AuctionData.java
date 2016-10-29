package com.klobliu.wow.model;

import com.klobliu.wow.model.Auc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klobliu on 2016/8/21.
 */

public class AuctionData {

    private HashMap<Integer, List<Auc>> mAucMap;

    public AuctionData() {
        mAucMap = new HashMap<>();
    }

    public void setData(List<Auc> aucs) {
        for (Auc auc : aucs) {
            List<Auc> cacheAucs = mAucMap.get(auc.item);
            if (cacheAucs != null) {
                cacheAucs.add(auc);
            } else {
                cacheAucs = new ArrayList<>();
                cacheAucs.add(auc);
                mAucMap.put(auc.item, cacheAucs);

            }
        }
        sort();
    }

    public HashMap<Integer, List<Auc>> getmAucMap() {
        return mAucMap;
    }

    private void sort() {
        for (Map.Entry<Integer, List<Auc>> integerListEntry : mAucMap.entrySet()) {

            Collections.sort(integerListEntry.getValue(), new Comparator<Auc>() {
                @Override
                public int compare(Auc lhs, Auc rhs) {
                    return (int) (lhs.buyout - rhs.buyout);
                }
            });

        }
    }

    public void setmAucMap(HashMap<Integer, List<Auc>> mAucMap) {
        this.mAucMap = mAucMap;
    }

    public List<Auc> getByItemId(int itemId) {
        return mAucMap.get(itemId);
    }
}
