package com.guikai.cniaoshop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.ShoppingCart;

import java.util.List;

public class WareOrderAdapter extends SimpleAdapter<ShoppingCart> {

    public WareOrderAdapter(Context context, List<ShoppingCart> datas) {
        super(context, R.layout.template_order_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, ShoppingCart item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
    }

    //计算商品总价
    public float getTotalPrice() {
        float sum = 0;
        if (!isNull())
            return sum;
        for (ShoppingCart cart : datas) {
            sum += cart.getCount()*cart.getPrice();
        }

        return sum;
    }

    private boolean isNull() {
        return (datas != null && datas.size() > 0);
    }
}
