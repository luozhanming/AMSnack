package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bean.ShoppingCar;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.bussiness.ShoppingCarCache;
import com.zhanming.amsnack.contract.MainContract;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/3.
 */

public class MainPresenter extends PresenterDelegate<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(Context ctx, MainContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        loadShoppingCar();
    }

    private void loadShoppingCar() {
        ShoppingBussiness.queryShoppingCar().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ShoppingCar>>() {
                    @Override
                    public void call(List<ShoppingCar> shoppingCars) {
                        if (shoppingCars.size() > 0) {
                            ShoppingCar shoppingCar = shoppingCars.get(0);
                            ShoppingCarCache.setLocalCar(shoppingCar);
                        } else {
                            ShoppingBussiness.addEmptyShoppingCar().subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<ShoppingCar>() {
                                        @Override
                                        public void call(ShoppingCar shoppingCar) {
                                            ShoppingCarCache.setLocalCar(shoppingCar);
                                        }
                                    });
                        }
                    }
                });
    }
}
