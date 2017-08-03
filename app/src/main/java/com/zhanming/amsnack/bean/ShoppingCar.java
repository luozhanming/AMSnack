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
    private Map<Good,Integer> count = new HashMap<>();


    public Boolean getCommit() {
        return isCommit;
    }

    public void setCommit(Boolean commit) {
        isCommit = commit;
    }


    public Map<Good, Integer> getCount() {
        return count;
    }

    public void setCount(Map<Good, Integer> count) {
        this.count = count;
    }
}
