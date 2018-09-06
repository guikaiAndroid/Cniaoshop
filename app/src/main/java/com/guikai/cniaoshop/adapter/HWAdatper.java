package com.guikai.cniaoshop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.Wares;

import java.util.List;

/*
 * Time:         2018/9/5 23:45
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    HWAdatper
 * Creator:      Anding
 * Note:         TODO
 */
public class HWAdatper extends SimpleAdapter<Wares> {


    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        viewHolder.getTextView(R.id.text_price).setText("￥"+wares.getPrice());
    }
}
