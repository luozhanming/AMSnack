package com.zhanming.amsnack.ui;

import android.os.Bundle;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.BaseMVPActivity;
import com.zhanming.amsnack.icontract.SignUpContract;
import com.zhanming.amsnack.presenter.SignUpPresenter;

public class SignUpActivity extends BaseMVPActivity<SignUpContract.Presenter> implements SignUpContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public SignUpContract.Presenter createPresenter() {
        return new SignUpPresenter(this, this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_sign_up;
    }


    @Override
    public void jump2Login() {

    }
}
