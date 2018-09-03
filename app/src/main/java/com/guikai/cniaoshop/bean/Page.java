package com.guikai.cniaoshop.bean;

import java.util.List;

/*
 * Time:         2018/9/3 8:15
 * Package_Name: com.guikai.cniaoshop.bean
 * File_Name:    Page
 * Creator:      Anding
 * Note:         热卖商品的json对应实体类 最外部外部
 */
public class Page<T> {

    private  int currentPage;
    private  int pageSize;
    private  int totalPage;
    private  int totalCount;

    private List<T> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
