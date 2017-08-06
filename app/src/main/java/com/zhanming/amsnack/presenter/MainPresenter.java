package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.contract.MainContract;

/**
 * Created by zhanming on 2017/8/3.
 */

public class MainPresenter extends PresenterDelegate<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(Context ctx, MainContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }
}
