package com.guikai.cniaoshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.guikai.cniaoshop.adapter.HWAdatper;
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
        initTab();
        getData();
    }

    private void initToolBar() {

    }

    //http
    private void getData() {
        pager = Pager.newBuilder().setUrl(Contants.API.WARES_CAMPAIGN_LIST)
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
        mToolbar = (CnToolbar) findViewById(R.id.toolbar);
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

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {

    }

    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {

    }

    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {

    }
}
