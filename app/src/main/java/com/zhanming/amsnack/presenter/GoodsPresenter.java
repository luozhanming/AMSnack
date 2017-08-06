package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.contract.GoodsContract;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/4.
 */

public class GoodsPresenter extends PresenterDelegate<GoodsContract.View> implements GoodsContract.Presenter {

    private Subscription queryHotSubscription;
    private Subscription queryNormalSubscription;
    private boolean hasLoaded = false;

    public GoodsPresenter(Context ctx, GoodsContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onStart() {
        super.onStart();
        if (!hasLoaded) {
            loadGood();
        }
    }

    private void loadGood() {
        mView.showLoading();
        queryHotSubscription = ShoppingBussiness.queryHotGoods(true).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Good>>() {
                    @Override
                    public void call(List<Good> goods) {
                        mView.loadHotGood(goods);
                    }
                });
        queryNormalSubscription = ShoppingBussiness.queryHotGoods(false).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Good>>() {
                    @Override
                    public void call(List<Good> goods) {
                        mView.loadNormalGood(goods);
                        hasLoaded = true;
                        mView.dismissLoading();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queryHotSubscription.unsubscribe();
        queryNormalSubscription.unsubscribe();
    }
}
