package com.zhanming.amsnack.bussiness;

import android.text.TextUtils;
import android.util.Log;

import com.zhanming.amsnack.bean.AppUser;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zhanming on 2017/8/3.
 */

public class UserBussiness {
    private static final String TAG = "UserBussiness";

    public static void signUp(String username, String password, String email) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(new SaveListener<AppUser>() {
            @Override
            public void done(AppUser appUser, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "注册成功");
                } else {
                    Log.e(TAG, "注册失败:" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }

    //    private BmobRelation likes;    //喜欢的商品
//    private String name;
//    private Integer age;
//    private String sex;
//    private Integer credit;
//    private BmobFile img;         //头像文件

    public static void modifyUserInformation(String name, Integer age, String sex, File img) {
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
        if (img != null) {
            BmobFile file = new BmobFile(img);
        }
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d(TAG, "修改成功");
                } else {
                    Log.e(TAG, "修改失败" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }

    public static void login(String username, String password) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<AppUser>() {
            @Override
            public void done(AppUser appUser, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "登录成功");
                } else {
                    Log.e(TAG, "登录失败" + e.getMessage() + "/" + e.getErrorCode());
                }
            }
        });
    }
}
