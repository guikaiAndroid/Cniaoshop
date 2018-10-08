package com.guikai.cniaoshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.WareListActivity;
import com.guikai.cniaoshop.decoration.CardViewtemDecortion;
import com.guikai.cniaoshop.adapter.HomeCatgoryAdapter;
import com.guikai.cniaoshop.bean.Banner;
import com.guikai.cniaoshop.bean.Campaign;
import com.guikai.cniaoshop.bean.HomeCampaign;
import com.guikai.cniaoshop.http.BaseCallback;
import com.guikai.cniaoshop.http.OkHttpHelper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    //轮播图的加载使用第三方库
    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;

    private static final String TAG="HomeFragment";

    //对OkHttp3的封装 单例模式
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        requestImages();

        initRecyclerView(view);
        return view;
    }

    private void requestImages() {

        String url = Contants.API.BANNER+"?type=1";

        httpHelper.get(url, new BaseCallback<List<Banner>>() {

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Call call, Response response, List<Banner> banners) {

                Log.e("首页HomeFragment轮播图数量: ",banners.size()+"");
                showSliderViews(banners);

            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void initRecyclerView(View view) {
        //从网络加载商品展示页
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onRequestBefore(Request request) { }

            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Call call, Response response, List<HomeCampaign> homeCampaigns) {
                Log.e("首页HomeFragment商品数量: ",homeCampaigns.size()+"");
                initData(homeCampaigns);
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) { }

        });

    }

    private void initData(List<HomeCampaign> homeCampaigns) {

        mAdatper = new HomeCatgoryAdapter(homeCampaigns, getActivity());

        //设置点击事件 跳转传值到商品列表页面
        mAdatper.setOncampaignClickListener(new HomeCatgoryAdapter.OncampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Intent intent =new Intent(getActivity(), WareListActivity.class);
                intent.putExtra(Contants.COMPAINGAIN_ID, campaign.getId());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.addItemDecoration(new CardViewtemDecortion());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }


    //引入轮播图github框架
    private void showSliderViews(List<Banner> banners) {

        if (banners != null) {
            for (Banner banner : banners) {

                DefaultSliderView sliderView = new DefaultSliderView(this.getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
            }
        }

        //原点动画
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //图片跳转动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        //时间
        mSliderLayout.setDuration(3000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSliderLayout.stopAutoCycle();
    }
}
