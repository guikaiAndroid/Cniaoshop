package com.guikai.cniaoshop.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guikai.cniaoshop.CniaoApplication;
import com.guikai.cniaoshop.LoginActivity;
import com.guikai.cniaoshop.bean.User;

public abstract class BaseFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = createView(inflater, container, savedInstanceState);

        initToolBar();

        init();

        return view;
    }

    public void initToolBar() {

    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void init();

    public void startActivity(Intent intent, boolean isNeedLogin){

        if (isNeedLogin) {
         User user = CniaoApplication.getmInstance().getUser();
         if (user != null) {
             super.startActivity(intent);
         }
         else {
             CniaoApplication.getmInstance().putIntent(intent);
             Intent LoginIntent = new Intent(getActivity(), LoginActivity.class);
             super.startActivity(LoginIntent);
         }
     } else {
            super.startActivity(intent);
        }

    }
}
