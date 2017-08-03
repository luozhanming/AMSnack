package com.zhanming.amsnack.icontract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;

/**
 * Created by zhanming on 2017/8/3.
 */

public interface SignUpContract {
    interface View extends BaseView{
        void jump2Login();
    }

    interface Presenter extends BasePresenter{
        void signUp();
    }
}
