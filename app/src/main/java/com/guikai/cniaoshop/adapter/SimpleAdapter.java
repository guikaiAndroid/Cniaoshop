package com.guikai.cniaoshop.adapter;

import android.content.Context;

import java.util.List;

/*
 * Time:         2018/9/5 23:09
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    SimpleAdapter
 * Creator:      Anding
 * Note:         TODO
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {

    public SimpleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public SimpleAdapter(Context context, int layoutResId, List<T> datas) {
        super(context, layoutResId, datas);
    }

}
