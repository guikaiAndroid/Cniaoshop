package com.guikai.cniaoshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.guikai.cniaoshop.bean.User;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.msg.LoginRespMsg;
import com.guikai.cniaoshop.utils.DESUtil;
import com.guikai.cniaoshop.utils.ToastUtils;
import com.guikai.cniaoshop.widget.ClearEditText;
import com.guikai.cniaoshop.widget.CnToolbar;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

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
    private Button mLogBtn;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mToolBar = (CnToolbar) findViewById(R.id.toolbar);
        mEtxtPhone = (ClearEditText) findViewById(R.id.etxt_phone);
        mEtxtPwd = (ClearEditText) findViewById(R.id.etxt_pwd);
        mLogBtn = (Button) findViewById(R.id.btn_login);


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtxtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show(getApplicationContext(),"请输入手机号码！");
                    return;
                }
                String pwd = mEtxtPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.show(getApplicationContext(),"请输入密码！");
                    return;
                }

                //将账号密码存入Map中，其中密码需要DES加密，
                Map<String, Object> params = new HashMap<>(2);
                params.put("phone",phone);
                params.put("password", DESUtil.encode(Contants.DES_KEY, pwd));

                //把map中的用户信息发送给服务器，获取数据
                okHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(getApplicationContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, LoginRespMsg<User> userLoginRespMsg) {
                        CniaoApplication application = CniaoApplication.getmInstance();
                        //将从服务器获取的存入本地share
                        ToastUtils.show(LoginActivity.this,userLoginRespMsg.getData()+""+userLoginRespMsg.getToken());
                        application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());

                        //登录成功 结束当前页面，返回值给上一个fragment 我的页面
                        if (application.getIntent() == null) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            application.jumpToTargetActivity(LoginActivity.this);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, int code, Exception e) {

                    }
                });
            }
        });
    }

    public void Reg(View view) {
        Intent intent = new Intent(this,RegActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
