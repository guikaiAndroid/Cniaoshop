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
import cn.sharesdk.onekeyshare.OnekeyShare;

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
        mToolBar = (CnToolbar) findViewById(R.id.toolbar);

        mToolBar.setNavigationOnClickListener(this);
        mToolBar.setRightButtonText("分享");

        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        mWebView.loadUrl(Contants.API.WARES_DETAIL);

        mAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterface, "appInterface");
        mWebView.setWebViewClient(new WC());

    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWare.getName());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(mWare.getImgUrl());//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.cniao5.com");

        // 启动分享GUI
        oks.show(this);
    }


    @Override
    public void onClick(View v) {
        this.finish();
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
