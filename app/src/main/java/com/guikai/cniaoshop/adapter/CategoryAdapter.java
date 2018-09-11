package com.guikai.cniaoshop.adapter;

import android.content.Context;

import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.Category;

import java.util.List;

/*
 * Time:         2018/9/11 23:29
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    CategoryAdapter
 * Creator:      Anding
 * Note:         TODO
 */
public class CategoryAdapter extends SimpleAdapter<Category>{


    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Category item) {
        viewHoder.getTextView(R.id.textView).setText(item.getName());
    }
}
