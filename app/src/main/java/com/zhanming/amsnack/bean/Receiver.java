package com.zhanming.amsnack.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhanming on 2017/7/31.
 */

public class Receiver extends BmobObject {
    private AppUser user;
    private String address;
    private String receiverName;
    private String phone;
    private Boolean hasSettled;  //是否设置为默认地址

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHasSettled() {
        return hasSettled;
    }

    public void setHasSettled(Boolean hasSettled) {
        this.hasSettled = hasSettled;
    }
}
