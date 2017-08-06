package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bussiness.UserBussiness;
import com.zhanming.amsnack.contract.LoginContract;

import java.util.Map;

import cn.bmob.v3.BmobUser;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/3.
 */

public class LoginPresenter extends PresenterDelegate<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(Context ctx, LoginContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppUser user = BmobUser.getCurrentUser(AppUser.class);
        if(user!=null){
            mView.jump2Main();
        }

    }

    @Override
    public void login() {
        Map<String, String> form = mView.getForm();
        String username = form.get("username");
        String password = form.get("password");
        UserBussiness.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppUser>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.showLoginMessage(false);
                    }

                    @Override
                    public void onNext(AppUser appUser) {
                        mView.showLoginMessage(true);
                        mView.jump2Main();
                    }
                });
    }
}
