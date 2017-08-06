package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;
import com.zhanming.amsnack.bean.Good;

import java.util.List;

/**
 * Created by zhanming on 2017/8/4.
 */

public interface GoodsContract {
    interface View extends BaseView {
        void showLoading();
        void dismissLoading();
        void loadHotGood(List<Good> goods);
        void loadNormalGood(List<Good> goods);
    }

    interface Presenter extends BasePresenter {

    }
}
