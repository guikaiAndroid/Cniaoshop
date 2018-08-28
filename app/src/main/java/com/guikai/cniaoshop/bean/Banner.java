package com.guikai.cniaoshop.bean;

/*
 * Time:         2018/8/27 23:24
 * Package_Name: com.guikai.cniaoshop.bean
 * File_Name:    Banner
 * Creator:      Anding
 * Note:         首页轮播图实体类
 */
public class Banner extends BaseBean {

    //根据获取的Json的数据 写一个相对于的实体类
    private  String name;
    private  String imgUrl;
    private  String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
