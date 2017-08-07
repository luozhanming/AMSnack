package com.zhanming.amsnack.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhanming on 2017/8/6.
 */

public class CarGoodCount extends BmobObject{

    private Integer count;
    private ShoppingCar car;
    private Good good;
    private Float totalPrice;
    private Order order;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ShoppingCar getCar() {
        return car;
    }

    public void setCar(ShoppingCar car) {
        this.car = car;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
