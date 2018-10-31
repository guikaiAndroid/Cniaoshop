package com.guikai.cniaoshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.guikai.cniaoshop.utils.ManifestUtil;
import com.guikai.cniaoshop.utils.ToastUtils;
import com.guikai.cniaoshop.widget.ClearEditText;
import com.guikai.cniaoshop.widget.CnToolbar;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import dmax.dialog.SpotsDialog;

public class RegActivity extends AppCompatActivity {

    public static final String TAG = "RegActivity";

    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";

    private CnToolbar mToolBar;
    private TextView mTxtCountry;
    private TextView mTxtCountryCode;
    private ClearEditText mEtxtPhone;
    private ClearEditText mEtxtPwd;
    private SpotsDialog dialog;

    private SMSEvenHanlder evenHanlder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();

        MobSDK.init(this, ManifestUtil.getMetaDataValue(this, "Mob-AppKey"),
                ManifestUtil.getMetaDataValue(this,"Mob-AppSecret"));

        //回调
        evenHanlder = new SMSEvenHanlder();
        SMSSDK.registerEventHandler(evenHanlder);

        String[] country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        if (country != null) {

            mTxtCountryCode.setText("+"+country[1]);
            mTxtCountry.setText(country[0]);
        }
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mTxtCountry = findViewById(R.id.txtCountry);
        mTxtCountryCode = findViewById(R.id.txtCountryCode);
        mEtxtPhone = findViewById(R.id.edittxt_phone);
        mEtxtPwd = findViewById(R.id.edittxt_pwd);

        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
    }

    private void getCode() {

        String phone = mEtxtPhone.getText().toString().trim().replace("\\s*", "");
        String code = mTxtCountryCode.getText().toString().trim();
        String pwd = mEtxtPwd.getText().toString().trim();

        //正则表达式检查格式是否正确
        checkPhoneNum(phone,code);

        //进行验证
        SMSSDK.getVerificationCode(code,phone);
    }

    private void checkPhoneNum(String phone, String code) {
        if (code.startsWith("+")) {
            code = code.substring(1);
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this,"请输入手机号码");
            return;
        }

        if (code == "86") {
            if (phone.length() != 11) {
                ToastUtils.show(this, "手机号码长度不对");
                return;
            }
        }

        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m =p.matcher(phone);

        if (!m.matches()) {
            ToastUtils.show(this, "您输入的手机号码格式不正确");
            return;
        }

    }

    //开启子线程 完成回调后的操作
    class SMSEvenHanlder extends EventHandler {
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {

                            onCountryListGot((ArrayList<HashMap<String, Object>>) data);

                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                            // 请求验证码后，跳转到验证码填写页面
                            afterVerificationCodeRequested((Boolean) data);

                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                        }
                    } else {

                        // 根据服务器返回的网络错误，给toast提示
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                ToastUtils.show(RegActivity.this, des);
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }
                    }
                }
            });
        }
    }

    private void onCountryListGot(ArrayList<HashMap<String, Object>> countries) {
        // 解析国家列表
        for (HashMap<String, Object> country : countries) {
            String code = (String) country.get("zone");
            String rule = (String) country.get("rule");
            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
                continue;
            }
            Log.d(TAG,"code="+code + "rule="+rule);
        }

    }

    /** 请求验证码后，跳转到验证码填写页面 */
    private void afterVerificationCodeRequested(boolean smart) {

        String phone = mEtxtPhone.getText().toString().trim().replaceAll("\\s*", "");
        String code = mTxtCountryCode.getText().toString().trim();
        String pwd = mEtxtPwd.getText().toString().trim();

        if (code.startsWith("+")) {
            code = code.substring(1);
        }

        Intent intent = new Intent(this,RegSecondActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("pwd",pwd);
        intent.putExtra("countryCode",code);

        startActivity(intent);
    }

    private String[] getCurrentCountry() {
        String mcc = getMCC();
        String[] country = null;
        if (!TextUtils.isEmpty(mcc)) {
            country = SMSSDK.getCountryByMCC(mcc);
        }

        if (country == null) {
            Log.w("SMSSDK", "no country found by MCC: " + mcc);
            country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        }
        return country;
    }

    private String getMCC() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // 返回当前手机注册的网络运营商所在国家的MCC+MNC. 如果没注册到网络就为空.
        String networkOperator = tm.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator;
        }

        // 返回SIM卡运营商所在国家的MCC+MNC. 5位或6位. 如果没有SIM卡返回空
        return tm.getSimOperator();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(evenHanlder);
    }
}
