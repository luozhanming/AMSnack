package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;

/**
 * Created by zhanming on 2017/8/5.
 */

public interface MineContract {
    interface View extends BaseView {
        void jump2ReceiverDetail();
    }

    interface Presenter extends BasePresenter {

    }
}
