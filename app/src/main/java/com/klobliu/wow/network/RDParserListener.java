package com.klobliu.wow.network;

import com.klobliu.wow.data.IParse;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-31  .
 * *********    Time : 00:35 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */

public abstract class RDParserListener<T> extends RDFindListener<T> {
    @Override
    public abstract IParse<T> createParser();
}
