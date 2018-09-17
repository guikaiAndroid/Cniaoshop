package com.guikai.cniaoshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.BaseAdapter;
import com.guikai.cniaoshop.adapter.CategoryAdapter;
import com.guikai.cniaoshop.decoration.DividerItemDecortion;
import com.guikai.cniaoshop.adapter.WaresAdapter;
import com.guikai.cniaoshop.bean.Banner;
import com.guikai.cniaoshop.bean.Category;
import com.guikai.cniaoshop.bean.Page;
import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.decoration.DividerGridItemDecoration;
import com.guikai.cniaoshop.http.BaseCallback;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class CategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SliderLayout mSliderLayout;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerviewWares;

    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdapter;

    private OkHttpHelper mHttphelper = OkHttpHelper.getInstance();

    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long category_id=0;

    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state = STATE_NORMAL;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_category,container,false);

        initView(view);
        requestCategoryData();
        requestBannerData();
        initRefreshLayout();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_category);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_category);
        mRecyclerviewWares = (RecyclerView) view.findViewById(R.id.recyclerview_wares);
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_layout);
    }

    private void initRefreshLayout(){

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
                Toast.makeText(getActivity(),"数据已经是最新的啦",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if(currPage <=2)
                    loadMoreData();
                else{
//                    Toast.makeText()
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private  void refreshData(){

        currPage =1;
        state=STATE_REFREH;
        requestWares(category_id);

    }

    private void loadMoreData(){
        currPage = ++currPage;
        state = STATE_MORE;
        requestWares(category_id);
    }



    private void requestCategoryData() {
        mHttphelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {

            @Override
            public void onSuccess(Call call, Response response, List<Category> categories) {
                showCategoryData(categories);

                if(categories !=null && categories.size()>0)
                    category_id = categories.get(0).getId();
                requestWares(category_id);
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void showCategoryData(List<Category> categories) {

        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        //通过CategoryAdapter回调接口，实现点击事件
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Category category = mCategoryAdapter.getItem(position);

                category_id = category.getId();
                currPage=1;
                state=STATE_NORMAL;

                requestWares(category_id);
            }
        });

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

    private void requestWares(long categoryId) {

        String url = Contants.API.WARES_LIST+"?categoryId="+categoryId+"&curPage="+currPage+"&pageSize="+pageSize;

        mHttphelper.get(url, new BaseCallback<Page<Wares>>() {


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
            public void onSuccess(Call call, Response response, Page<Wares> waresPage) {

                currPage = waresPage.getCurrentPage();
                totalPage = waresPage.getTotalPage();
                showWaresData(waresPage.getList());
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void showWaresData(List<Wares> wares) {

        switch (state){

            case  STATE_NORMAL:

                if(mWaresAdapter ==null) {
                    mWaresAdapter = new WaresAdapter(getContext(), wares);

                    mRecyclerviewWares.setAdapter(mWaresAdapter);

                    mRecyclerviewWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mRecyclerviewWares.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerviewWares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                }
                else{
                    mWaresAdapter.clear();
                    mWaresAdapter.addData(wares);
                }

                break;

            case STATE_REFREH:
                mWaresAdapter.clear();
                mWaresAdapter.addData(wares);

                mRecyclerviewWares.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                mWaresAdapter.addData(mWaresAdapter.getDatas().size(),wares);
                mRecyclerviewWares.scrollToPosition(mWaresAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
