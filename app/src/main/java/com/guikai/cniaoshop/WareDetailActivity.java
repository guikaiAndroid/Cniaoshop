package com.guikai.cniaoshop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guikai.cniaoshop.bean.Wares;
import com.guikai.cniaoshop.utils.CartProvider;
import com.guikai.cniaoshop.utils.ToastUtils;
import com.guikai.cniaoshop.widget.CnToolbar;

import java.io.Serializable;

import dmax.dialog.SpotsDialog;

/*
 * Time:         2018/10/11 8:33
 * Package_Name: com.guikai.cniaoshop
 * File_Name:    WareDetailActivity
 * Creator:      Anding
 * Note:         TODO
 */
public class WareDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView mWebView;
    private CnToolbar mToolBar;
    private Wares mWare;
    private CartProvider cartProvider;
    private AlertDialog mDialog;
    private WebAppInterface mAppInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        initView();

        Serializable serializable = getIntent().getSerializableExtra(Contants.WARE);
        if (serializable == null)
            this.finish();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("loading....")
                .build();
        mDialog.show();

        mWare = (Wares) serializable;
        cartProvider = new CartProvider(this);




    }

    protected void initView() {
        mWebView = (WebView) findViewById(R.id.webView);


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        mWebView.loadUrl(Contants.API.WARES_DETAIL);

        mAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterface, "appInterface");
        mWebView.setWebViewClient(new WC());

    }

    @Override
    public void onClick(View v) {

    }

    class WC extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();

            mAppInterface.showDetail();
        }
    }

    class WebAppInterface {

        private Context mContext;
        public WebAppInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void showDetail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail("+mWare.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id) {
            cartProvider.put(mWare);
            ToastUtils.show(mContext,"已添加进购物车");
        }

        @JavascriptInterface
        public void addFavorites(Long id) {

        }

    }
}
