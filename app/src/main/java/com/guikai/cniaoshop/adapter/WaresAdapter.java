package com.guikai.cniaoshop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.Wares;

import java.util.List;

public class WaresAdapter extends SimpleAdapter<Wares> {

    public WaresAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.template_grid_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Wares item) {
        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        viewHoder.getTextView(R.id.text_price).setText("￥"+item.getPrice());
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
    }
}
