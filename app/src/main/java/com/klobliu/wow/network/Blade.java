package com.klobliu.wow.network;

import com.alibaba.fastjson.JSON;
import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.okhttp.FileDownloader;
import com.diandi.klob.sdk.processor.Processor;
import com.diandi.klob.sdk.processor.WorkHandler;
import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;
import com.klobliu.wow.BladeManager;
import com.klobliu.wow.data.IParse;
import com.klobliu.wow.db.ItemDao;
import com.klobliu.wow.model.Auc;
import com.klobliu.wow.model.AuctionData;
import com.klobliu.wow.model.HotItem;
import com.klobliu.wow.model.Item;
import com.klobliu.wow.model.ItemBase;
import com.klobliu.wow.model.ItemData;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-14  .
 * *********    Time : 22:12 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class Blade {
    private static final String TAG = "Blade";
    private final static String DOMAIN = "http://www.bnade.com/";
    private final static String AUCTION = "auction/item/";
    private final static String ITEM = "wow/item/";
    private final static String ITEM_NAME = "wow/item/name/";
    private final static String PAST = "wow/auction/past/realm/";
    private final static String TOP_ITEM = "wow/item/hotsearch?offset=0&limit=100";
    private int skip = 0;

    public static String encode(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /***
     * http://content.battlenet.com.cn/wow/icons/56/inv_enchant_shardbrilliantlarge.jpg
     * 物品图片
     */
    public static String getIcon(String icon) {
        String url = "http://content.battlenet.com.cn/wow/icons/56/" + icon + ".jpg";
        return url;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    private void getItemByName(String item, StringCallback callback) {
        String url = null;
        url = DOMAIN + ITEM_NAME + encode(item);
        RD.get(url, callback);
    }

    public void getItemByName(String itemName, final RDGetListener<ItemBase> callback) {
        String url = null;
        url = DOMAIN + ITEM_NAME + encode(itemName);
        ItemBase base = ItemDao.getInstance().queryByParam("url", url);
        L.d(TAG, base);
        if (base != null && base.id != 0) {
            callback.onResult(base, false);
        } else {
            final String finalUrl = url;
            RD.getWithCache(url, new RDGetListener<ItemBase>() {
                @Override
                public void onResult(ItemBase o, boolean isError) {
                    callback.onResult(o, isError);
                    callback.onResult(o, isError);
                    if (!isError) {
                        o.url = finalUrl;
                        ItemDao.getInstance().create(o);
                    }
                }
            });
        }
    }

    public void getItemIdByName(String item, final IntCallBack callBack) {
        String url = null;
        url = DOMAIN + ITEM_NAME + encode(item);
        RD.get(url, new RDGetListener<Item>() {
            @Override
            public void onResult(Item item, boolean isError) {
                callBack.onSuccess(item.id);
            }
        });
    }

    public void itemQueryByName(String realm, String item) {
        String url = null;
        url = DOMAIN + ITEM_NAME + encode(item);
        RD.get(url, null);
    }

    public void itemQueryByName(String realm, String item, RDGetListener<Item> listener) {
        String url = null;
        url = DOMAIN + ITEM_NAME + encode(item);
        RD.get(url, null);
    }

    public void accurateQuery(String realm, int itemId, String itemName) {

    }

    /**
     * http://www.bnade.com/wow/auction/past/realm/136/item/128315
     */
    public static void getPast24ById(int itemId, RDFindListener<ItemData> listener) {
        String url = DOMAIN + PAST + getRealmId() + "/item/" + itemId;
        RD.get(url, listener);
    }

    public void getPast24ByName(String name, final RDFindListener<ItemData> listener) {
        getItemByName(name, new RDGetListener<ItemBase>() {
            @Override
            public void onResult(ItemBase item, boolean isError) {
                if (isError) {
                    listener.onResult(null, isError);
                    return;
                }
                getPast24ById(item.id, listener);
            }
        });

    }

    /**
     * "wow/auction/history/realm/" + realmId + "/item/" + itemId,
     */
    public void getHistoryById(int itemId, RDFindListener<ItemData> listener) {
        String url = DOMAIN + "wow/auction/history/realm/" + getRealmId() + "/item/" + itemId;
        RD.get(url, listener);
    }

    @Deprecated
    public void getHistoryByName(String name, final RDFindListener<ItemData> listener) {
        getItemByName(name, new RDGetListener<ItemBase>() {
            @Override
            public void onResult(ItemBase item, boolean isError) {
                if (isError) {
                    listener.onResult(null, isError);
                    return;
                }
                getPast24ById(item.id, listener);
            }
        });

    }

    @Deprecated
    public void getAllInfos(final RDFindListener<Auc> listener) {
        System.gc();
        RD.get("http://auction-api-cn.worldofwarcraft.com/auction-data/2a17d22ec00efe3d3ce910c79e019600/auctions.json", new RDFindListener<Auc>() {
            @Override
            public void onResult(List<Auc> aucs, boolean isError) {
                if (listener != null) {
                    listener.onResult(aucs, isError);
                }
            }


            @Override
            public IParse<Auc> getParser() {
                return new IParse<Auc>() {
                    @Override
                    public List<Auc> parse(String response) {
                        System.gc();
                        L.e(TAG);
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                        System.gc();
                        List<Auc> aucs = JSON.parseArray(jsonObject.getString("auctions"), Auc.class);
                        L.e(TAG, aucs);
                        System.gc();
                        AuctionData data = new AuctionData();
                        data.setData(aucs);
                        L.e(TAG, data.getByItemId(128315));
                        return aucs;
                    }
                };
            }

            @Override
            public void onResponse(String response, int id) {
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                // L.d(TAG, jsonObject.get("realms"));

             /*   File file = FileUtils.getExternalStoragePath("auc.txt");
                try {
                    file.createNewFile();
                    FileUtils.writeStringToFile(file, aucs.toString(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }

        });
    }

    public void getAllInfosFromFile(final RDFindListener<Auc> listener) {
        FileDownloader.download(FileUtils.getExternalStoragePath(), "http://auction-api-cn.worldofwarcraft.com/auction-data/2a17d22ec00efe3d3ce910c79e019600/auctions.json", new FileDownloader.DownloadCallBack() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(final File file) {
                Global.showMsg(file.getAbsolutePath());
                Processor.execute(new WorkHandler() {
                    @Override
                    public void start() {
                        try {
                            System.gc();
                            String content = FileUtils.getStringFromFile(new FileInputStream(file));
                            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(content);
                            System.gc();
                            List<Auc> aucs = JSON.parseArray(jsonObject.getString("auctions"), Auc.class);
                            AuctionData data = new AuctionData();
                            data.setData(aucs);
                            L.e(TAG, data.getByItemId(128315));
                            listener.onResult(aucs, false);
                            System.gc();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void over() {

                    }
                });

            }

            @Override
            public void inProgress(float progress, long total) {

            }
        });
    }

    public static int getRealmId() {
        return BladeManager.getInstance().getRealmId();
    }

    /**
     * 热搜查询
     * http://www.bnade.com/wow/item/hotsearch?offset=0&limit=100
     */
    public void loadTopItems(RDFindListener<HotItem> listener) {
        String url = DOMAIN + TOP_ITEM;
        RD.get(url, listener);
    }

    /**
     * http://www.bnade.com/wow/item/name/%E5%86%9B%E5%9B%A2?fuzzy=true
     * 模糊查询
     */
    public void fuzzyQueryItems(String itemName) {
        String url = DOMAIN + ITEM_NAME + encode(itemName) + "?fuzzy=true";
        RD.test(url);
    }

    /**
     * http://www.bnade.com/wow/item/128315?tooltip=true
     * 物品详细
     */
    public void loadItemDetail(long id, StringCallback callback) {
        String url = DOMAIN + ITEM + id + "?tooltip=true";
        RD.get(url, callback);
    }
}
