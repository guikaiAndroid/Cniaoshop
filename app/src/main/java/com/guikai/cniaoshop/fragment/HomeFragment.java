package com.guikai.cniaoshop.fragment;

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

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.DividerItemDecortion;
import com.guikai.cniaoshop.adapter.HomeCatgoryAdapter;
import com.guikai.cniaoshop.bean.HomeCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;



    private static final  String TAG="HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        requestImages();
//        initSlider();
//
//
//
//        initRecyclerView(view);
        return view;
    }

    private void requestImages() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e("HomeFragment", "xxxxxxxxx");
            }

            @Override
            public void onResponse(@NonNull Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("HomeFragment", "json="+json );

                }
            }
        });



        }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("热门活动", R.drawable.img_big_1, R.drawable.img_0_small1,R.drawable.img_1_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);

        mAdatper = new HomeCatgoryAdapter(datas);

        mRecyclerView.setAdapter(mAdatper);

        mRecyclerView.addItemDecoration(new DividerItemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }



    //引入轮播图github框架
    private void initSlider() {

        TextSliderView textSliderView = new TextSliderView(this.getContext());
        textSliderView.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        textSliderView.description("新品推荐");
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

                Toast.makeText(HomeFragment.this.getActivity(),"新品推荐",Toast.LENGTH_LONG).show();

            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
        textSliderView2.description("时尚男装");


        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

                Toast.makeText(HomeFragment.this.getActivity(),"时尚男装",Toast.LENGTH_LONG).show();

            }
        });


        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");
        textSliderView3.description("家电秒杀");


        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView baseSliderView) {

                Toast.makeText(HomeFragment.this.getActivity(),"家电秒杀",Toast.LENGTH_LONG).show();
            }
        });

        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);

        //原点动画
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //图片跳转动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        //时间
        mSliderLayout.setDuration(3000);

        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


                Log.d(TAG,"onPageScrolled");

            }

            @Override
            public void onPageSelected(int i) {

                Log.d(TAG,"onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                Log.d(TAG,"onPageScrollStateChanged");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }
}
