package com.guikai.cniaoshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.ShoppingCart;
import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.utils.CartProvider;
import com.guikai.cniaoshop.utils.ToastUtils;

import java.util.List;

/*
 * Time:         2018/9/5 23:45
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    HWAdatper
 * Creator:      Anding
 * Note:         TODO
 */
public class HWAdatper extends SimpleAdapter<Wares> {

    CartProvider provider;

    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);

    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
        viewHolder.getTextView(R.id.text_price).setText("￥"+wares.getPrice());
        Button button= viewHolder.getButton(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider = new CartProvider(context);
                provider.put(convertData(wares));
                ToastUtils.show(context,"已添加到购物车");
            }
        });
    }

    public ShoppingCart convertData(Wares item) {
        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }
}
