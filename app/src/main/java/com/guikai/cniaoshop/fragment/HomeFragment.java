package com.guikai.cniaoshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.gson.Gson;
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
import com.guikai.cniaoshop.widget.CustomSliderView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;

    private static final String TAG="HomeFragment";

    private Gson mGson = new Gson();

    private List<Banner> mBanner;

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        requestImages();

        initRecyclerView(view);
        return view;
    }

    private void requestImages() {

        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";

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

                Log.e("banner: ",banners.size()+"");
                mBanner = banners;

                initSlider();
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
                Log.e("首页homeCampaigns商品显示成功: ",homeCampaigns.size()+"");
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
    private void initSlider() {

        if (mBanner != null) {
            for (Banner banner : mBanner) {

                CustomSliderView customSliderView = new CustomSliderView(getActivity());
                customSliderView
                        .description(banner.getName())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                mSliderLayout.addSlider(customSliderView);
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
