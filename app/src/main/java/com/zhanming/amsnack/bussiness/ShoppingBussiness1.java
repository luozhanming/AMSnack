package com.zhanming.amsnack.bussiness;

import android.util.Log;

import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.bean.ShoppingCar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

/**
 * Created by zhanming on 2017/8/3.
 */

public class ShoppingBussiness1 {

    private static final String TAG = "ShoppingBussiness";


    /**
     * 查询热门商品
     */
    public static Observable<List<Good>> queryHotGoods(boolean isHot) {
        final List<Good> goods = new ArrayList<>();
        BmobQuery<Good> query = new BmobQuery<Good>();
        query.addWhereEqualTo("isHot", isHot);
        Observable<List<Good>> observable = query.findObjectsObservable(Good.class);
        return observable;
    }

    /**
     * 将货物添加到购物车
     */
    public static void addGoodToShoppingCar(Good good, int count) {
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        if (localCar == null) {
            ShoppingCar car = new ShoppingCar();
            Map<Good, Integer> goods = new HashMap<Good, Integer>();
            goods.put(good, count);
            car.setCount(goods);
            car.setCommit(false);
            ShoppingCarCache.setLocalCar(car);
        } else {
            Map<Good, Integer> goods1 = localCar.getCount();
            goods1.put(good, count);
            localCar.setCommit(false);
        }
        ShoppingCarCache.saveToBmob();
    }

    /**
     * 生成订单
     */
    public static Observable<String> generateOrder(ShoppingCar car) {
        Order order = new Order();
        order.setShoppingCar(car);
        order.setHasHandle(false);
        order.setOrderOwner(BmobUser.getCurrentUser(AppUser.class));
        Map<Good, Integer> count = car.getCount();
        Set<Map.Entry<Good, Integer>> entries = count.entrySet();
        float totalPrice = 0;
        for (Map.Entry<Good, Integer> entry : entries) {
            totalPrice += entry.getValue();
        }
        order.setTotalPrice(totalPrice);
        Observable<String> observable = order.saveObservable();
        return observable;
    }

    /**
     * 查询商家未处理订单
     */
    public static Observable<List<Order>> queryUnhandleOrder(boolean hasHandle) {
        final List<Order> orders = new ArrayList<>();
        BmobQuery<Order> query = new BmobQuery();
        query.addWhereEqualTo("hasHandle", hasHandle);
        return query.findObjectsObservable(Order.class);
    }

    /**
     * 取消订单
     */
    public static Observable<Void> canceleOrder(Order order) {
        return order.deleteObservable(order.getObjectId());
    }

    /**
     * 添加收货地址
     */
    public static Observable<String> addReceiver(String receiverName, String address, String phone) {
        Receiver receiver = new Receiver();
        receiver.setUser(BmobUser.getCurrentUser(AppUser.class));
        receiver.setAddress(address);
        receiver.setHasSettled(false);
        receiver.setReceiverName(receiverName);
        receiver.setPhone(phone);
        return receiver.saveObservable();
    }

    /**
     * 将收获地址设置为默认
     */
    public static Observable<Void> settleReceiver(Receiver receiver) {
        final UpdateListener updateListener = new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "更新成功");
                } else {
                    Log.e(TAG, "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        };
        BmobQuery<Receiver> query = new BmobQuery<>();
        query.addWhereEqualTo("hasSettled", true);
        query.findObjects(new FindListener<Receiver>() {
            @Override
            public void done(List<Receiver> list, BmobException e) {
                if (e == null) {
                    for (Receiver receiver1 : list) {
                        receiver1.setHasSettled(false);
                        receiver1.update(updateListener);
                    }
                }
            }
        });
        receiver.setHasSettled(true);
        return receiver.updateObservable();
    }

    /**
     * 查询所有收货地址
     */
    public static Observable<List<Receiver>> queryAllReceiver() {
        final List<Receiver> receivers = new ArrayList<>();
        BmobQuery<Receiver> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(AppUser.class));
        return query.findObjectsObservable(Receiver.class);
    }

    /**
     * 修改收货地址
     */
    public static Observable<Void> modifyReceiver(Receiver receiver) {
        return receiver.updateObservable();
    }

    /**
     * 删除收货地址
     */
    public static Observable<Void> deleteReceiver(Receiver receiver) {
        return receiver.deleteObservable(receiver.getObjectId());
    }

}