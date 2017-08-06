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

    private static HashMap<Good, Integer> cache = new HashMap<>();

    public static ShoppingCarChangedListener mListener;

    public interface ShoppingCarChangedListener{
        void onShoppingCarChanged(Good good,int quantity);
    }

    public void setListener(ShoppingCarChangedListener listener){
        mListener = listener;
    }

    public static void putGoodToCar(Good good, int quantity) {
        cache.put(good, quantity);
        if(mListener!=null){
            mListener.onShoppingCarChanged(good,quantity);
        }
    }

    public static void clearShoppingCar() {
        cache.clear();
    }

    public static int getGoodQuantity(Good good) {
        return cache.get(good);
    }

}
