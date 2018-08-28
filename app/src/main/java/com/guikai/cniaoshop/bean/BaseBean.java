package com.guikai.cniaoshop.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {


    protected   long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
