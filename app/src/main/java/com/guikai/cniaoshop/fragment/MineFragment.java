package com.guikai.cniaoshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.LoginActivity;
import com.guikai.cniaoshop.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseFragment implements View.OnClickListener{

    private CircleImageView mImageHead;
    private TextView mTexUserName;
    private Button mbtnLogout;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mImageHead = (CircleImageView) view.findViewById(R.id.img_head);
        mTexUserName = (TextView) view.findViewById(R.id.text_username);
        mImageHead.setOnClickListener(this);
        mTexUserName.setOnClickListener(this);
    }


    @Override
    public void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head:
            case R.id.text_username:
                toLogin();

        }
    }

    private void toLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent,Contants.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
