package com.zhanming.amsnack.base;

import android.support.annotation.LayoutRes;

/**
 * Created by zhanming on 2017/7/31.
 */
public interface IMVPView {
    @LayoutRes
    int getLayoutID();
    BasePresenter createPresenter();

}