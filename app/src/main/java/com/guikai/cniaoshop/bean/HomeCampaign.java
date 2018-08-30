package com.guikai.cniaoshop.bean;

import java.io.Serializable;

/*
 * Time:         2018/8/30 23:37
 * Package_Name: com.guikai.cniaoshop.bean
 * File_Name:    HomeCampaign
 * Creator:      Anding
 * Note:         首页Homepage Json对应实体类
 */
public class HomeCampaign implements Serializable {


    private Long id;
    private String title;
    private Campaign cpOne;
    private Campaign cpTwo;
    private Campaign cpThree;


    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }


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
}
