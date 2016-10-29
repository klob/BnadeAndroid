package com.klobliu.wow.data;

import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-08-16  .
 * *********    Time : 22:33 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public interface IParse<T> {
    List<T> parse(String response);
}
