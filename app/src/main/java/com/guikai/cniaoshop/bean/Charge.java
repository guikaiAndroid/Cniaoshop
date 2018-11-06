package com.guikai.cniaoshop.bean;

/*
 * Time:         2018/11/6 23:37
 * Creator:      Anding
 * Note:         TODO
 */

import java.util.Map;

public class Charge {
    String id;
    String object;
    Long created;
    Boolean livemode;
    Boolean paid;
    Boolean refunded;
    Object app;
    String channel;
    String orderNo;
    String clientIp;
    Integer amount;
    Integer amountSettle;
    String currency;
    String subject;
    String body;
    Long timePaid;
    Long timeExpire;
    Long timeSettle;
    String transactionNo;
    ChargeRefundCollection refunds;
    Integer amountRefunded;
    String failureCode;
    String failureMsg;
    Map<String, String> metadata;
    Map<String, Object> credential;
    Map<String, String> extra;
    String description;
}
