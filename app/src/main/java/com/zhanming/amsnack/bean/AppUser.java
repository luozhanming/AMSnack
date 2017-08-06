package com.zhanming.amsnack.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by zhanming on 2017/7/31.
 */

public class AppUser extends BmobUser {

    private BmobRelation likes;    //喜欢的商品
    private String name;
    private Integer age;
    private String sex;
    private Integer credit;
    private BmobFile img;         //头像文件

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BmobFile getImg() {
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
