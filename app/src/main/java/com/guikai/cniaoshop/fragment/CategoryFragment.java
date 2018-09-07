package com.guikai.cniaoshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
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

    private OkHttpHelper mHttphelper = OkHttpHelper.getInstance();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category,container,false);
        return view;
    }

    private void requestCategoryData() {
        mHttphelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {

            @Override
            public void onSuccess(Call call, Response response, List<Category> categories) {
                showCategoryData(categories);

                if ((categories != null) && categories.size()>0)
                    
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }
}
