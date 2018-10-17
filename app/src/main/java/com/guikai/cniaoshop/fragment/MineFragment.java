package com.guikai.cniaoshop.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guikai.cniaoshop.R;

public class MineFragment extends BaseFragment {

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container,false);
    }

    @Override
    public void init() {

    }
}
