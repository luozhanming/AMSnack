package com.zhanming.amsnack;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by zhanming on 2017/7/30.
 */

public class MyApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"bf603c6f018e06b2d8516053bcb6480d");
    }


}
