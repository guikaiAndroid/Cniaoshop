package com.guikai.cniaoshop.bean;

import java.io.Serializable;

/*
 * Time:         2018/9/3 7:58
 * Package_Name: com.guikai.cniaoshop.bean
 * File_Name:    Wares
 * Creator:      Anding
 * Note:         热卖商品的实体类 内部对象 外部为Page
 */
public class Wares implements Serializable {

    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private Float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
