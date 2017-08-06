package com.zhanming.amsnack.contract;

import com.zhanming.amsnack.base.BasePresenter;
import com.zhanming.amsnack.base.BaseView;
import com.zhanming.amsnack.bean.Receiver;

import java.util.List;

/**
 * Created by zhanming on 2017/8/6.
 */

public interface ReceiverContract {

    interface View extends BaseView {
        void showLoading();
        void setReceivers(List<Receiver> datas);
        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        void addAdress();
    }
}
