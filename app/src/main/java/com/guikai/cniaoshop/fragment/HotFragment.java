package com.guikai.cniaoshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.http.OkHttpHelper;

public class HotFragment extends Fragment {

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_hot,container,false);



        return view;
    }

    private void getData() {

        String url = Contants.API.WARES_HOT + "?curPage=" + currPage

        httpHelper.get();
    }
}
