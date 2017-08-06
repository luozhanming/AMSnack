package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;

/**
 * Created by zhanming on 2017/8/3.
 */

public interface MainContract {
    interface View extends BaseView {
        void changeBadgeItemNumber(int num);
        void toggleBadgeItem(boolean isHide);
    }

    interface Presenter extends BasePresenter {

    }
}
