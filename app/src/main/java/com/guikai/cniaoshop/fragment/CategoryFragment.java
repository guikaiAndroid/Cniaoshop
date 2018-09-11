package com.guikai.cniaoshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.CategoryAdapter;
import com.guikai.cniaoshop.adapter.DividerItemDecortion;
import com.guikai.cniaoshop.bean.Category;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;

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
        mRecyclerView = view.findViewById(R.id.recyclerview_category);


        requestCategoryData();
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
}
