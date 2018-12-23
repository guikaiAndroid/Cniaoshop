package com.guikai.cniaoshop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.Campaign;
import com.guikai.cniaoshop.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCatgoryAdapter extends RecyclerView.Adapter<HomeCatgoryAdapter.ViewHolder> {

    private final static int VIEW_TYPE_L = 0;
    private final static int VIEW_TYPE_R = 1;

    private List<HomeCampaign> mDatas;
    private Context mContext;

    private OncampaignClickListener mListener;

    public HomeCatgoryAdapter(List<HomeCampaign> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mContext = context;
    }

    public void setOncampaignClickListener(OncampaignClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflate = LayoutInflater.from(viewGroup.getContext());

        if (i == VIEW_TYPE_R) {
            return new ViewHolder(mInflate.inflate(R.layout.template_home_cardview2,viewGroup,false));
        }

        return new ViewHolder(mInflate.inflate(R.layout.template_home_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        HomeCampaign homeCampaign = mDatas.get(i);
        viewHolder.textTitle.setText(homeCampaign.getTitle());
//        viewHolder.imageViewBig.setImageResource(category.getCpOne().getImaUrl());
//        viewHolder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
//        viewHolder.imageViewsSmallBottom.setImageResource(category.getImgSmallbottom());
        //使用picasso框架 从网络下载图片缓存 然后显示到界面
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(viewHolder.imageViewBig);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(viewHolder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaign.getCpThree().getImgUrl()).into(viewHolder.imageViewsSmallBottom);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewsSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                anim(view);
            }
        }

        private  void anim(final View v){

            ObjectAnimator animator =  ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                    .setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    HomeCampaign campaign = mDatas.get(getLayoutPosition());

                    switch (v.getId()){

                        case  R.id.imgview_big:
                            mListener.onClick(v, campaign.getCpOne());
                            break;

                        case  R.id.imgview_small_top:
                            mListener.onClick(v, campaign.getCpTwo());
                            break;

                        case R.id.imgview_small_bottom:
                            mListener.onClick(v,campaign.getCpThree());
                            break;
                    }
                }
            });
            animator.start();
        }
    }

    //为首页商品图片添加点击事件的监听
    public interface OncampaignClickListener {

        void onClick(View view, Campaign campaign);
    }

}
