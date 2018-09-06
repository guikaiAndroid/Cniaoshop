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
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.DividerItemDecortion;
import com.guikai.cniaoshop.adapter.HWAdatper;
import com.guikai.cniaoshop.adapter.HotWaresAdapter;
import com.guikai.cniaoshop.bean.Page;
import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HotFragment extends Fragment {

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private List<Wares> datas;
//    private HotWaresAdapter mAdapter;
    private HWAdatper mAdapter;

    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;

    private  static final int STATE_NORMAL=0;   //正常加载
    private  static final int STATE_REFREH=1;   //顶部下拉刷新
    private  static final int STATE_MORE=2;     //底部上拉刷新

    private int state = STATE_NORMAL;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_hot,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRefreshLayout = view.findViewById(R.id.refresh_view);
        initRefreshLayout();
        getData();

        return view;
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
                Toast.makeText(getActivity(),"数据已经是最新的啦",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if(currPage <= 2){
                    Log.e("onRefreshLoadMore",currPage+"xxx"+totalPage);
                    loadMoreData();}
                else{
                    Log.e("finishRefreshLoadMore()",currPage+"xxx"+totalPage);
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currPage = 1;

        state = STATE_REFREH;
        getData();
    }

    private void loadMoreData() {

        currPage = ++currPage;
        Log.e("loadMoreData()","currPage"+currPage);
        state = STATE_MORE;

        getData();
    }

    private void getData() {

        String url = Contants.API.WARES_HOT + "?curPage=" + currPage +"&pageSize=" +pageSize;

        httpHelper.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {

            @Override
            public void onSuccess(Call call, Response response, Page<Wares> waresPage) {

                datas = waresPage.getList();
                currPage = waresPage.getCurrentPage();
                totalPage =waresPage.getTotalPage();
                showData();
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {

        switch (state) {
            //正常情况下获取数据
            case STATE_NORMAL:
//                mAdapter = new HotWaresAdapter(datas);
//
//                mRecyclerView.setAdapter(mAdapter);
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), DividerItemDecortion.VERTICAL_LIST));
                mAdapter = new HWAdatper(getContext(),datas);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), DividerItemDecortion.VERTICAL_LIST));
                break;

                //下拉刷新时 首先清空所有数据 重新从服务器获取数据 然后recy滚动到顶部，然后隐藏刷新控件
            case STATE_REFREH:
                mAdapter.clear();
                mAdapter.addData(datas);

                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();    //关闭控件
                break;

                //底部上拉刷新
            case STATE_MORE:
                mAdapter.addData(mAdapter.getDatas().size(),datas);   //从结束位置，继续想adapter里的数据源增加数据
                mRecyclerView.scrollToPosition(mAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;

        }



    }
}
