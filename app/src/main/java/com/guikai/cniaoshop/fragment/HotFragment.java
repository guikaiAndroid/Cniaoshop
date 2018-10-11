package com.guikai.cniaoshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.reflect.TypeToken;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.WareDetailActivity;
import com.guikai.cniaoshop.adapter.BaseAdapter;
import com.guikai.cniaoshop.decoration.DividerItemDecortion;
import com.guikai.cniaoshop.adapter.HWAdatper;
import com.guikai.cniaoshop.bean.Page;
import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.utils.Pager;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HotFragment extends BaseFragment implements Pager.OnPageListener{

    private HWAdatper mAdapter;

    private RecyclerView mRecyclerView;
    //引用第三方库 下拉刷新和加载更多数据
    private MaterialRefreshLayout mRefreshLayout;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_hot,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRefreshLayout = view.findViewById(R.id.refresh_view);
        return view;
    }

    @Override
    public void init() {
        //封装分页逻辑
        Pager pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(this)
                .setPageSize(10)
                .setRefreshLayout(mRefreshLayout)
                .build(getContext(),new TypeToken<Page<Wares>>(){}.getType());
        pager.request();
    }

    @Override
    public void load(List datas, int totalPage, int totalCount) {
        mAdapter = new HWAdatper(getContext(),datas);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Wares wares = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);

                intent.putExtra(Contants.WARE, wares);
                startActivity(intent);

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), DividerItemDecortion.VERTICAL_LIST));
    }

    @Override
    public void refresh(List datas, int totalPage, int totalCount) {
        mAdapter.refreshData(datas);

        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void loadMore(List datas, int totalPage, int totalCount) {
        mAdapter.loadMoreData(datas);
        mRecyclerView.scrollToPosition(mAdapter.getDatas().size());
    }

}
