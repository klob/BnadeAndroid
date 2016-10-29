package com.klobliu.wow.model;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-16  .
 * *********    Time : 21:59 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class Auc {
    //public long auc;
    public int item;
    public long bid;
    public long buyout;
    public short quantity;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(builder.append("Auc{")).append("item=").append(item).append(", bid=").append(bid).append(", buyout=").append(buyout).append(", quantity=").append(quantity).append("}\n");
        return builder.toString();
    }
}
