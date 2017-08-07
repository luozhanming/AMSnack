package com.zhanming.amsnack.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhanming on 2017/7/31.
 */

public class Order extends BmobObject {

    private ShoppingCar shoppingCar;
    private AppUser orderOwner;
    private Boolean hasHandle;
    private Float totalPrice;
    private Receiver receiver;

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public AppUser getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(AppUser orderOwner) {
        this.orderOwner = orderOwner;
    }


    public Boolean getHasHandle() {
        return hasHandle;
    }

    public void setHasHandle(Boolean hasHandle) {
        this.hasHandle = hasHandle;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
