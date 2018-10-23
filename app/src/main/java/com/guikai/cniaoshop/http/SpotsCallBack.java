package com.guikai.cniaoshop.http;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.guikai.cniaoshop.CniaoApplication;
import com.guikai.cniaoshop.LoginActivity;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.utils.ToastUtils;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private Context mContext;

    private AlertDialog mDialog;

    public SpotsCallBack(Context context) {
        mContext = context;
        initSpotsDialog();
    }

    private void initSpotsDialog() {
        mDialog = new SpotsDialog.Builder()
                      .setContext(mContext)
                      .setMessage("数据加载中...")
                      .build();
    }

    public void showDialog(){
        mDialog.show();
    }

    public void dismissDialog(){
        mDialog.dismiss();
    }

    public void setLoadMessage(int resId){
        mDialog.setMessage(mContext.getString(resId));
    }

    @Override
    public void onFailure(Call call, IOException e) {
        dismissDialog();
    };

    @Override
    public void onRequestBefore(Request request) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onTokenError(Response response, int code) {
        ToastUtils.show(mContext, code+"");
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);

        CniaoApplication.getmInstance().clearUser();
    }
}
