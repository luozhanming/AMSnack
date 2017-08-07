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
    private boolean defaultAddress;

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

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
