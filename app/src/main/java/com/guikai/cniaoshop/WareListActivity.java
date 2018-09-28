package com.guikai.cniaoshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.guikai.cniaoshop.adapter.HWAdatper;
import com.guikai.cniaoshop.adapter.decoration.DividerItemDecortion;
import com.guikai.cniaoshop.bean.Page;
import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.utils.Pager;
import com.guikai.cniaoshop.widget.CnToolbar;

import java.util.List;

public class WareListActivity extends AppCompatActivity implements Pager.OnPageListener<Wares>,
        TabLayout.OnTabSelectedListener, View.OnClickListener{

    private static final String TAG = "WareListActivity";

    public static final int TAG_DEFAULT = 0;
    public static final int TAG_SALE = 1;
    public static final int TAG_PRICE = 2;

    public static final int ACTION_LIST = 1;
    public static final int ACTION_GIRD = 2;

    private TabLayout mTabLayout;
    private TextView mTxtSummary;
    private RecyclerView mRecyclerView_wares;
    private MaterialRefreshLayout mRefreshLayout;
    private CnToolbar mToolbar;

    private int orderBy = 0;
    private long campaignId = 0;

    private Pager pager;

    private HWAdatper mWaresAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelist);

        initView();

        initToolBar();

        campaignId = getIntent().getLongExtra(Contants.COMPAINGAIN_ID,0);

        initTab();

        getData();
    }

    private void initToolBar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareListActivity.this.finish();
            }
        });

        mToolbar.setRightButtonIcon(R.drawable.icon_grid_32);
        mToolbar.getRightButton().setTag(ACTION_LIST);
        mToolbar.setRightButtonOnClickListener(this);
    }

    //http
    private void getData() {
        pager= Pager.newBuilder().setUrl(Contants.API.WARES_CAMPAIN_LIST)
                .putParam("campaignId",campaignId)
                .putParam("orderBy",orderBy)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(this)
                .build(this,new TypeToken<Page<Wares>>(){}.getType());

        pager.request();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTxtSummary = (TextView) findViewById(R.id.txt_summary);
        mRecyclerView_wares = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mToolbar = (CnToolbar) findViewById(R.id.tool_bar);
    }

    private void initTab() {

        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("销量");
        tab.setTag(TAG_SALE);
        mTabLayout.addTab(tab);

        mTabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderBy = (int) tab.getTag();
        pager.putParam("orderBy", orderBy);
        pager.request();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();

        if(ACTION_LIST == action){

            mToolbar.setRightButtonIcon(R.drawable.icon_list_32);
            mToolbar.getRightButton().setTag(ACTION_GIRD);

            mWaresAdapter.resetLayout(R.layout.template_grid_wares);

            mRecyclerView_wares.setLayoutManager(new GridLayoutManager(this,2));

        }
        else if(ACTION_GIRD == action){

            mToolbar.setRightButtonIcon(R.drawable.icon_grid_32);
            mToolbar.getRightButton().setTag(ACTION_LIST);
            mWaresAdapter.resetLayout(R.layout.template_hot_wares);

            mRecyclerView_wares.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {
        mTxtSummary.setText("共有"+totalCount+"件商品");

        if (mWaresAdapter == null) {
            mWaresAdapter = new HWAdatper(this, datas);
            mRecyclerView_wares.setAdapter(mWaresAdapter);
            mRecyclerView_wares.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView_wares.addItemDecoration(new DividerItemDecoration(this, DividerItemDecortion.VERTICAL_LIST));
            mRecyclerView_wares.setItemAnimator(new DefaultItemAnimator());
        } else {
            mWaresAdapter.refreshData(datas);
        }

    }

    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {
        mWaresAdapter.refreshData(datas);
        mRecyclerView_wares.scrollToPosition(0);
    }

    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {
        mWaresAdapter.loadMoreData(datas);
    }
}
