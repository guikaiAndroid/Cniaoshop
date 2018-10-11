package com.guikai.cniaoshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.utils.CartProvider;
import com.guikai.cniaoshop.widget.CnToolbar;

import dmax.dialog.SpotsDialog;

/*
 * Time:         2018/10/11 8:33
 * Package_Name: com.guikai.cniaoshop
 * File_Name:    WareDetailActivity
 * Creator:      Anding
 * Note:         TODO
 */
public class WareDetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private CnToolbar mToolBar;
    private Wares mWares;
    private CartProvider cartProvider;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
    }
}
