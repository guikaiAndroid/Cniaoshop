package com.guikai.cniaoshop.bean;

import java.io.Serializable;

public class Address implements Serializable,Comparable<Address> {

    private Long id;

    private String consignee;
    private String phone;
    private String addr;
    private String zipCode;
    private Boolean isDefault;

    public Address() {};

    public Address(String consignee, String phone, String addr, String zipCode) {
        this.consignee = consignee;
        this.phone = phone;
        this.addr = addr;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public int compareTo(Address another) {

        if (another.getDefault() != null && this.getDefault() != null)
            return another.getDefault().compareTo(this.getDefault());

        return -1;
    }
}
