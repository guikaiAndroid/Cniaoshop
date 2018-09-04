package com.guikai.cniaoshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
 * Time:         2018/9/4 23:38
 * Package_Name: com.guikai.cniaoshop.adapter
 * File_Name:    BaseAdapter
 * Creator:      Anding
 * Note:         公共的基类Adapter
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{

    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutResId;

    public BaseAdapter(Context context, List<T> datas, int layoutResId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutResId = layoutResId;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mLayoutResId,null,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        T t = getItem(i);
        bindData(baseViewHolder,t);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //onBindViewHolder 数据与layouItem中的View绑定需要调用者完成，故需要成抽象方法
    public abstract void bindData(BaseViewHolder viewHolder, T t);

    public T getItem(int position) {
        return mDatas.get(position);
    }
}
