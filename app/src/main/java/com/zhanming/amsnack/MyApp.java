package com.zhanming.amsnack;

import android.app.Application;

import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.bean.ShoppingCar;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.bussiness.ShoppingCarCache;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/7/30.
 */

public class MyApp extends Application {

    public static Receiver receiver = null;


    @Override
    public void onCreate() {
        super.onCreate();
        BmobConfig config = new BmobConfig.Builder(this).setConnectTimeout(5000)
                .setApplicationId("bf603c6f018e06b2d8516053bcb6480d").build();
        Bmob.initialize(config);


    }


}
