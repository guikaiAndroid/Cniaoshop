package com.guikai.cniaoshop.bean;

import java.io.Serializable;

/*
 * Time:         2018/8/30 23:33
 * Package_Name: com.guikai.cniaoshop.bean
 * File_Name:    Campaign
 * Creator:      Anding
 * Note:         首页Homefragment加载 子列表
 */
public class Campaign implements Serializable {


    private Long id;
    private String title;
    private String imgUrl;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
