package com.zhanming.amsnack.bean;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by zhanming on 2017/7/31.
 */

public class ShoppingCar extends BmobObject {

    private Boolean isCommit = false;   //是否生成订单
    private AppUser user;
    private Order order;


    public Boolean getCommit() {
        return isCommit;
    }

    public void setCommit(Boolean commit) {
        isCommit = commit;
    }



    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
