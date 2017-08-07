package com.zhanming.amsnack.bussiness;

import android.util.Log;

import com.zhanming.amsnack.MyApp;
import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bean.CarGoodCount;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.bean.ShoppingCar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhanming on 2017/8/3.
 */

public class ShoppingBussiness {

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
    public static Observable<String> addGoodToShoppingCar(Good good, int quantity) {
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        CarGoodCount counter = new CarGoodCount();
        counter.setCar(localCar);
        counter.setGood(good);
        counter.setCount(quantity);
        counter.setTotalPrice(quantity * good.getPrice());
        return counter.saveObservable();
    }

    /**
     * 查询购物车商品
     */
    public static Observable<List<CarGoodCount>> queryCarGoodCounter(ShoppingCar car) {
        BmobQuery<CarGoodCount> query = new BmobQuery<>();
        query.addWhereEqualTo("car", car);
        query.include("good");
        return query.findObjectsObservable(CarGoodCount.class);
    }

    /**
     * 更新购物车商品shuliang
     */
    public static void updateCarGoodCounter(CarGoodCount counter, int quantity) {
        counter.setCount(quantity);
        counter.setTotalPrice(counter.getGood().getPrice() * quantity);
        counter.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "更新成功");
                }
            }
        });
    }

    public static void commitShoppingCar(){
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        localCar.setCommit(true);
        localCar.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d(TAG, "提交成功");
                }
            }
        });
    }

    /**
     * 清空购物车
     */
    public static Observable<Boolean> clearShoppingCar(ShoppingCar car) {
        BmobQuery<CarGoodCount> query = new BmobQuery<>();
        query.addWhereEqualTo("car", car);
        return query.findObjectsObservable(CarGoodCount.class)
                .flatMap(new Func1<List<CarGoodCount>, Observable<CarGoodCount>>() {
                    @Override
                    public Observable<CarGoodCount> call(List<CarGoodCount> carGoodCounts) {
                        return Observable.from(carGoodCounts);
                    }
                })
                .map(new Func1<CarGoodCount, Boolean>() {
                    @Override
                    public Boolean call(CarGoodCount carGoodCount) {
                        carGoodCount.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.d(TAG, "清理成功");
                                }
                            }
                        });
                        return true;
                    }
                });
    }

    /**
     * 添加空购物车
     */
    public static Observable<ShoppingCar> addEmptyShoppingCar() {
        ShoppingCar car = new ShoppingCar();
        car.setUser(BmobUser.getCurrentUser(AppUser.class));
        car.setCommit(false);
        return car.saveObservable().flatMap(new Func1<String, Observable<ShoppingCar>>() {
            @Override
            public Observable<ShoppingCar> call(String s) {
                BmobQuery<ShoppingCar> query = new BmobQuery<ShoppingCar>();
                return query.getObjectObservable(ShoppingCar.class, s);
            }
        });
    }

    public static Observable<List<ShoppingCar>> queryShoppingCar() {
        BmobQuery<ShoppingCar> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(AppUser.class));
        query.addWhereEqualTo("isCommit", false);
        return query.findObjectsObservable(ShoppingCar.class);
    }


    public static Observable<Order> generateOrder(ShoppingCar car) {
        final Order order = new Order();
        order.setHasHandle(false);
        order.setShoppingCar(car);
        order.setOrderOwner(BmobUser.getCurrentUser(AppUser.class));
        order.setReceiver(MyApp.receiver);
        order.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "订单暂时保存成功");
                }
            }
        });
        BmobQuery<CarGoodCount> query = new BmobQuery<>();
        query.addWhereEqualTo("car", car);
        query.addWhereNotEqualTo("count", 0);
        query.include("good");
        return query.findObjectsObservable(CarGoodCount.class)
                .flatMap(new Func1<List<CarGoodCount>, Observable<Order>>() {
                    @Override
                    public Observable<Order> call(List<CarGoodCount> carGoodCounts) {
                        float totalPrice = 0;
                        for (CarGoodCount counter : carGoodCounts) {
                            totalPrice += counter.getTotalPrice();
                            counter.setOrder(order);
                            counter.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.d(TAG, "更新成功");
                                    }
                                }
                            });
                            //更新商品库存
                            Good good = counter.getGood();
                            int oldStore = good.getStore();
                            int oldSold = good.getSellCount();
                            good.setStore(oldStore - counter.getCount());
                            good.setSellCount(oldSold + counter.getCount());
                            good.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Log.d(TAG, "更新商品信息成功");
                                }
                            });
                        }
                        order.setTotalPrice(totalPrice);
                        order.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.d(TAG, "更新成功");
                                }
                            }
                        });
                        return Observable.just(order);
                    }
                });

    }

    /**
     * 查询商家未处理订单
     */
    public static Observable<List<Order>> queryOrder(boolean hasHandle) {
        BmobQuery<Order> query = new BmobQuery();
        query.addWhereEqualTo("hasHandle", hasHandle);
        query.include("receiver");
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
        //   receiver.setHasSettled(false);
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
                        receiver1.setDefaultAddress(false);
                        receiver1.update(updateListener);
                    }
                }
            }
        });
        receiver.setDefaultAddress(true);
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


    /**
     * 查询本用户喜欢的商品
     */
    public static Observable<List<Good>> queryLoveGoods() {
        BmobQuery<Good> query = new BmobQuery<>();
        AppUser user = BmobUser.getCurrentUser(AppUser.class);
        query.addWhereEqualTo("likes", new BmobPointer(user));
        return query.findObjectsObservable(Good.class);
    }

    /**
     * 查询喜欢商品的用户
     */
    public static Observable<List<AppUser>> queryLoveGoodUser(Good good) {
        BmobQuery<AppUser> query = new BmobQuery<>();
        query.addWhereEqualTo("likes", new BmobPointer(good));
        return query.findObjectsObservable(AppUser.class);
    }

    /**
     * 添加喜欢商品
     */

    public static Observable<Void> addLoveGoods(Good good) {
        AppUser user = BmobUser.getCurrentUser(AppUser.class);
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        good.setLikes(relation);
        Observable observable1 = good.updateObservable();
        BmobRelation relation1 = new BmobRelation();
        relation1.add(good);
        user.setLikes(relation1);
        Observable observable2 = user.updateObservable();
        return Observable.merge(observable1, observable2);
    }

    /**
     * 取消喜欢的商品
     */

    public static Observable<Void> cancelLoveGoods(Good good) {
        AppUser user = BmobUser.getCurrentUser(AppUser.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(user);
        good.setLikes(relation);
        Observable o1 = good.updateObservable();
        BmobRelation r2 = new BmobRelation();
        r2.remove(good);
        user.setLikes(r2);
        Observable o2 = user.updateObservable();
        return Observable.merge(o1, o2);
    }

}
