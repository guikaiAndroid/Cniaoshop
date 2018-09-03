package com.guikai.cniaoshop.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.Wares;

import java.util.List;

/*
 * Time:         2018/9/2 23:03
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    HotWaresAdapter
 * Creator:      Anding
 * Note:         热卖主页的适配器
 */
public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private List<Wares> mDatas;

    private LayoutInflater mInflater;

    public HotWaresAdapter(List<Wares> wares) {
        mDatas = wares;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = LayoutInflater.from(viewGroup.getContext());
        View view = mInflater.inflate(R.layout.template_hot_wares, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Wares wares = mDatas.get(i);

        viewHolder.draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        viewHolder.textTitle.setText(wares.getName());
        viewHolder.textPrice.setText("￥"+wares.getPrice());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView draweeView;
        TextView textTitle;
        TextView textPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.text_title);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textPrice =(TextView) itemView.findViewById(R.id.text_price);
        }
    }

}
