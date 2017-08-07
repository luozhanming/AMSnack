package com.zhanming.amsnack.bussiness;

import android.text.TextUtils;
import android.util.Log;

import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bean.ShoppingCar;

import java.util.HashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zhanming on 2017/8/2.
 */

public class ShoppingCarCache {

   private static ShoppingCarCache CACHE = new ShoppingCarCache();
    private static ShoppingCar localCar;

    public static ShoppingCar getLocalCar() {
        return localCar;
    }

    public static void setLocalCar(ShoppingCar localCar) {
        ShoppingCarCache.localCar = localCar;
    }
}
