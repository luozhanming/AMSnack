package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.icontract.SignUpContract;

/**
 * Created by zhanming on 2017/8/3.
 */

public class SignUpPresenter extends PresenterDelegate<SignUpContract.View> implements SignUpContract.Presenter{


    public SignUpPresenter(Context ctx, SignUpContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void signUp() {

    }
}
