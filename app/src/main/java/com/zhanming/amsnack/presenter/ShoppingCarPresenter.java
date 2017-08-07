package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bean.CarGoodCount;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.bean.ShoppingCar;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.bussiness.ShoppingCarCache;
import com.zhanming.amsnack.contract.ShoppingCarContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.bmob.v3.BmobRealTimeData.TAG;

/**
 * Created by zhanming on 2017/8/6.
 */

public class ShoppingCarPresenter extends PresenterDelegate<ShoppingCarContract.View> implements ShoppingCarContract.Presenter {

    public ShoppingCarPresenter(Context ctx, ShoppingCarContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mView.showLoading();
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        ShoppingBussiness.queryCarGoodCounter(localCar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CarGoodCount>>() {
                    @Override
                    public void call(List<CarGoodCount> carGoodCounts) {
                        mView.setDatas(carGoodCounts);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void cancelCar() {
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        ShoppingBussiness.clearShoppingCar(localCar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        mView.clearDatas();
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    @Override
    public void commitCar() {
        ShoppingCar localCar = ShoppingCarCache.getLocalCar();
        ShoppingBussiness.generateOrder(localCar)
                .map(new Func1<Order, Boolean>() {
                    @Override
                    public Boolean call(Order order) {
                        boolean b = order.getReceiver() != null;
                        if (!b) {
                            order.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.d("ShoppingCarPresenter", "订单撤销");
                                    }
                                }
                            });
                        }
                        return b;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            mView.showNoReceiverAlert();
                        } else {
                            Toast.makeText(mContext, "订单生成", Toast.LENGTH_SHORT).show();
                            mView.clearDatas();
                            //更换购物车
                            changeShoppingCar();
                        }
                    }
                });
    }

    private void changeShoppingCar() {
        ShoppingBussiness.commitShoppingCar();
        ShoppingBussiness.addEmptyShoppingCar().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ShoppingCar>() {
                    @Override
                    public void call(ShoppingCar car) {
                        ShoppingCarCache.setLocalCar(car);
                    }
                });
    }
}
