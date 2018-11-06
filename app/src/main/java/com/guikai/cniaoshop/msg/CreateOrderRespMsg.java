package com.guikai.cniaoshop.msg;

/*
 * Time:         2018/11/6 23:34
 * Creator:      Anding
 * Note:         TODO
 */

public class CreateOrderRespMsg extends BaseRespMsg {

    private OrderRespMsg data;

    public OrderRespMsg getData() {
        return data;
    }

    public void setData(OrderRespMsg data) {
        this.data = data;
    }
}
