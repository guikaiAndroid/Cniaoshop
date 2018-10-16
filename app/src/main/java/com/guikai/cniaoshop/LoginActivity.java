package com.guikai.cniaoshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.widget.ClearEditText;
import com.guikai.cniaoshop.widget.CnToolbar;

/*
 * Time:         2018/10/16 23:37
 * Package_Name: com.guikai.cniaoshop
 * File_Name:    LoginActivity
 * Creator:      Anding
 * Note:         TODO
 */
public class LoginActivity extends AppCompatActivity {

    private CnToolbar mToolBar;
    private ClearEditText mEtxtPhone;
    private ClearEditText mEtxtPwd;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
