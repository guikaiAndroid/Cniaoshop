package com.guikai.cniaoshop.fragment;

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

public class HotFragment extends Fragment {

    private HWAdatper mAdapter;

    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_hot,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRefreshLayout = view.findViewById(R.id.refresh_view);

        Pager pager = Pager.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(new Pager.OnPageListener() {
                    @Override
                    public void load(List datas, int totalPage, int totalCount) {
                        mAdapter = new HWAdatper(getContext(),datas);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), DividerItemDecortion.VERTICAL_LIST));
                    }

                    @Override
                    public void refresh(List datas, int totalPage, int totalCount) {
                        mAdapter.clear();
                        mAdapter.addData(datas);
                        mRecyclerView.scrollToPosition(0);
                    }

                    @Override
                    public void loadMore(List datas, int totalPage, int totalCount) {
                        mAdapter.addData(mAdapter.getDatas().size(),datas);   //从结束位置，继续想adapter里的数据源增加数据
                        mRecyclerView.scrollToPosition(mAdapter.getDatas().size());
                    }
                })
                .setPageSize(10)
                .setRefreshLayout(mRefreshLayout)
                .build(getContext(),new TypeToken<Page<Wares>>(){}.getType());
        pager.request();

        return view;
    }
}
