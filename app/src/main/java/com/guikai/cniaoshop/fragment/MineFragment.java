package com.guikai.cniaoshop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guikai.cniaoshop.CniaoApplication;
import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.LoginActivity;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.bean.User;
import com.squareup.picasso.Picasso;

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
        mbtnLogout = view.findViewById(R.id.btn_logout);
        mImageHead.setOnClickListener(this);
        mTexUserName.setOnClickListener(this);
        mbtnLogout.setOnClickListener(this);
    }


    @Override
    public void init() {
        User user = CniaoApplication.getmInstance().getUser();
        showUser(user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head:
                toLogin();
                break;
            case R.id.text_username:
                toLogin();
                break;
            case R.id.btn_logout:
                toLogout();
                break;
        }
    }

    private void toLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent,Contants.REQUEST_CODE);
    }

    private void toLogout() {
        CniaoApplication.getmInstance().clearUser();
        showUser(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user = CniaoApplication.getmInstance().getUser();
        showUser(user);
    }

    private void showUser(User user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.getLogo_url())){
                mTexUserName.setText(user.getUsername());
                Picasso.with(getActivity()).load(user.getLogo_url()).into(mImageHead);
                mbtnLogout.setVisibility(View.VISIBLE);
            }
        } else {
            mTexUserName.setText(R.string.to_login);
            mImageHead.setImageResource(R.drawable.default_head);
            mbtnLogout.setVisibility(View.GONE);
        }
    }
}
