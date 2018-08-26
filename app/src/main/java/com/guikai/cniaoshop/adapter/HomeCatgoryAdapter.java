package com.guikai.cniaoshop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.HomeCategory;

import java.util.List;

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private LayoutInflater mInflate;

    private List<HomeCategory> mDatas;

    public HomeCatgoryAdapter(List<HomeCategory> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflate = LayoutInflater.from(viewGroup.getContext());

        if (i == VIEW_TYPE_R) {
            return new ViewHolder(mInflate.inflate(R.layout.template_home_cardview2,viewGroup,false));
        }

        return new ViewHolder(mInflate.inflate(R.layout.template_home_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        HomeCategory category = mDatas.get(i);
        viewHolder.textTitle.setText(category.getName());
        viewHolder.imageViewBig.setImageResource(category.getImgBig());
        viewHolder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
        viewHolder.imageViewsSmallBottom.setImageResource(category.getImgSmallbottom());
    }


    @Override
    public int getItemViewType(int position) {

        if (position % 2 ==0){
            return VIEW_TYPE_R;
        }
        return VIEW_TYPE_L;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewsSmallBottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewsSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
        }
    }
}
