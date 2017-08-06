package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;

import java.util.Map;

/**
 * Created by zhanming on 2017/8/3.
 */

public interface SignUpContract {
    interface View extends BaseView{
        Map<String,String> getForm();
        void jump2Login();
        void jump2Main();
        void showSignUpCode(boolean isSuccessful);
    }

    interface Presenter extends BasePresenter{
        void signUp();
    }
}
