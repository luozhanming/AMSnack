package com.zhanming.amsnack.bussiness;

import android.text.TextUtils;
import android.util.Log;

import com.zhanming.amsnack.bean.ShoppingCar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zhanming on 2017/8/2.
 */

public class ShoppingCarCache {
    private static ShoppingCar localCar;

    public static void setLocalCar(ShoppingCar car) {
        localCar = car;
    }

    public static ShoppingCar getLocalCar() {
        return localCar;
    }

    public static void saveToBmob() {
        String objectId = localCar.getObjectId();
        if (TextUtils.isEmpty(objectId)) {
            ShoppingCar car = new ShoppingCar();
            car.setCommit(localCar.getCommit());
            car.setCount(car.getCount());
            car.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.d("ShoppingCarCache", "保存成功 ");
                    }else{
                        Log.d("ShoppingCarCache", "保存失败，错误代码："+e.getErrorCode());
                    }

                }
            });
        }else{
            ShoppingCar car = new ShoppingCar();
            car.setObjectId(objectId);
            car.setCommit(localCar.getCommit());
            car.setCount(car.getCount());
            car.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.d("ShoppingCarCache", "保存成功 ");
                    }else{
                        Log.d("ShoppingCarCache", "保存失败，错误代码："+e.getErrorCode());
                    }
                }
            });
        }
    }
}
