package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;
import com.zhanming.amsnack.bean.CarGoodCount;

import java.util.List;

/**
 * Created by zhanming on 2017/8/6.
 */

public interface ShoppingCarContract {

    interface View extends BaseView{
        void showLoading();
        void hideLoading();
        void clearDatas();
        void showNoReceiverAlert();
        void setDatas(List<CarGoodCount> datas);
    }

    interface Presenter extends BasePresenter{
        void cancelCar();
        void commitCar();
    }
}
