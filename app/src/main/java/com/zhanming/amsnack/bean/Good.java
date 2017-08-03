package com.zhanming.amsnack.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by zhanming on 2017/7/31.
 */

public class Good extends BmobObject {

    private BmobRelation loads;  //装载商品
    private BmobRelation likes;
    private String name;
    private Float price;
    private String imgUrl;
    private String description;
    private Integer store;
    private Boolean isHot;

    public BmobRelation getLoader() {
        return loads;
    }

    public void setLoader(BmobRelation loader) {
        this.loads = loader;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }
}
