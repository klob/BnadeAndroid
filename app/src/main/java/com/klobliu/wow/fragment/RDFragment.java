package com.klobliu.wow.fragment;

import com.klobliu.wow.network.Blade;
import com.klobliu.wow.network.RDFindListener;

/**
 * Created by klobliu on 2016/8/29.
 */

public abstract class RDFragment<T> extends BaseListFragment<T> {


    //泛型查询
    protected Blade mBlade = new Blade();

    protected RDFindListener<T> mInitFindListener;
    protected RDFindListener<T> mLoadMoreFindListener;
}
