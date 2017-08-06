package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;

import java.util.Map;

/**
 * Created by zhanming on 2017/8/3.
 */

public interface LoginContract {
    interface View extends BaseView {
        Map<String, String> getForm();
        void showLoginMessage(boolean isSuccessful);
        void jump2Main();
        void jump2SignUp();
    }

    interface Presenter extends BasePresenter {
        void login();
    }
}
