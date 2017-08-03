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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zhanming on 2017/8/2.
 */

public class ShoppingBussiness {
    private static final String TAG = "ShoppingBussiness";

    /**
     * 查询热门商品
     */
    public static List<Good> queryHotGoods(boolean isHot) {
        final List<Good> goods = new ArrayList<>();
        BmobQuery<Good> query = new BmobQuery<Good>();
        query.addWhereEqualTo("isHot", isHot);
        query.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> list, BmobException e) {
                if (e == null) {
                    goods.addAll(list);
                } else {
                    Log.d(TAG, "查询操作错误，错误代码：" + e.getErrorCode());
                }
            }
        });
        return goods;
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
    public static void generateOrder(ShoppingCar car) {
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
        order.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "生成订单成功:" + s);
                } else {
                    Log.d(TAG, "生成订单失败:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询商家未处理订单
     */
    public static List<Order> queryUnhandleOrder(boolean hasHandle) {
        final List<Order> orders = new ArrayList<>();
        BmobQuery<Order> query = new BmobQuery();
        query.addWhereEqualTo("hasHandle", hasHandle);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "查询订单成功");
                    orders.addAll(list);
                } else {
                    Log.e(TAG, "查询订单失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        return orders;
    }

    /**
     * 取消订单
     */
    public static void canceleOrder(Order order) {
        order.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "取消成功");
                } else {
                    Log.e(TAG, "取消失败：" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 添加收货地址
     */
    public static void addReceiver(String receiverName, String address, String phone) {
        Receiver receiver = new Receiver();
        receiver.setUser(BmobUser.getCurrentUser(AppUser.class));
        receiver.setAddress(address);
        receiver.setHasSettled(false);
        receiver.setReceiverName(receiverName);
        receiver.setPhone(phone);
        receiver.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "添加地址成功");
                } else {
                    Log.e(TAG, "添加地址失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 将收获地址设置为默认
     */
    public static void settleReceiver(Receiver receiver) {
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
        receiver.update(updateListener);
    }

    /**
     * 查询所有收货地址
     */
    public static List<Receiver> queryAllReceiver() {
        final List<Receiver> receivers = new ArrayList<>();
        BmobQuery<Receiver> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(AppUser.class));
        query.findObjects(new FindListener<Receiver>() {
            @Override
            public void done(List<Receiver> list, BmobException e) {
                if (e == null) {
                    receivers.addAll(list);
                } else {
                    Log.d(TAG, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        return receivers;
    }

    /**
     * 修改收货地址
     */
    public static void modifyReceiver(Receiver receiver) {
        receiver.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "更新成功");
                } else {
                    Log.d(TAG, "更新失败：" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 删除收货地址
     */
    public static void deleteReceiver(Receiver receiver) {
        receiver.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "删除成功");
                } else {
                    Log.e(TAG, "删除失败：" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }


}
