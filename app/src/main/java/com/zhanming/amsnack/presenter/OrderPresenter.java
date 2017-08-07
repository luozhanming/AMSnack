package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.contract.OrderContract;

/**
 * Created by zhanming on 2017/8/7.
 */

public class OrderPresenter extends PresenterDelegate<OrderContract.View> implements OrderContract.Presenter {
    public OrderPresenter(Context ctx, OrderContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }
}
