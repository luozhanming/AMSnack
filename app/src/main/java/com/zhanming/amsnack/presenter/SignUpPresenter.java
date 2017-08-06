package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bussiness.UserBussiness;
import com.zhanming.amsnack.contract.SignUpContract;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/3.
 */

public class SignUpPresenter extends PresenterDelegate<SignUpContract.View> implements SignUpContract.Presenter {


    public SignUpPresenter(Context ctx, SignUpContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void signUp() {
        Map<String, String> form = mView.getForm();
        String username = form.get("username");
        String password = form.get("password");
        String email = form.get("email");
        UserBussiness.signUp(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.showSignUpCode(false);
                    }

                    @Override
                    public void onNext(AppUser appUser) {
                        mView.showSignUpCode(true);
                        mView.jump2Main();
                    }
                });

    }
}
