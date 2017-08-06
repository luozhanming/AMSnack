package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.contract.MainContract;
import com.zhanming.amsnack.contract.MineContract;

/**
 * Created by zhanming on 2017/8/5.
 */

public class MinePresenter extends PresenterDelegate<MineContract.View> implements MineContract.Presenter {

    public MinePresenter(Context ctx, MineContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }
}
