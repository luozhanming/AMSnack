package com.zhanming.amsnack.bussiness;

import android.text.TextUtils;
import android.util.Log;

import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bean.ShoppingCar;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

/**
 * Created by zhanming on 2017/8/3.
 */

public class UserBussiness {
    private static final String TAG = "UserBussiness";




    public static Observable<AppUser> signUp(String username, String password, String email) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setCredit(100);
        user.setEmail(email);
        return user.signUpObservable(AppUser.class);
    }

    public static Observable<Void> modifyUserInformation(String name, int age, String sex,int credit) {
        AppUser user = BmobUser.getCurrentUser(AppUser.class);
        if (!TextUtils.isEmpty(name)) {
            user.setName(name);
        }
        if (age != 0) {
            user.setAge(age);
        }
        if (!TextUtils.isEmpty(sex)) {
            user.setSex(sex);
        }
        if(credit!=0){
            user.setCredit(credit);
        }
        return user.updateObservable();
    }

    public static Observable<AppUser> login(String username, String password) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        return user.loginObservable(AppUser.class);
    }
}
