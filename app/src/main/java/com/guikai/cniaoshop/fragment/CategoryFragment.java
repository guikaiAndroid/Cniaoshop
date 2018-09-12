package com.guikai.cniaoshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.CategoryAdapter;
import com.guikai.cniaoshop.adapter.DividerItemDecortion;
import com.guikai.cniaoshop.bean.Banner;
import com.guikai.cniaoshop.bean.Category;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.widget.CustomSliderView;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class CategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SliderLayout mSliderLayout;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerviewWare;

    private CategoryAdapter mCategoryAdapter;

    private OkHttpHelper mHttphelper = OkHttpHelper.getInstance();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_category,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_category);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_category);
        requestCategoryData();
        requestBannerData();
        return view;
    }



    private void requestCategoryData() {
        mHttphelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {

            @Override
            public void onSuccess(Call call, Response response, List<Category> categories) {
                showCategoryData(categories);

            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);

        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecortion.VERTICAL_LIST));
    }

    private void requestBannerData() {
        String url = Contants.API.BANNER+"?type=1";

        mHttphelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Call call, Response response, List<Banner> banners) {
                Log.e("Category Banner",""+banners.size());
                showSliderViews(banners);
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
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


        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }
}
