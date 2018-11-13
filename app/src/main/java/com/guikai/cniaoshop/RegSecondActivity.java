package com.guikai.cniaoshop;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guikai.cniaoshop.bean.User;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.msg.LoginRespMsg;
import com.guikai.cniaoshop.utils.CountTimerView;
import com.guikai.cniaoshop.utils.DESUtil;
import com.guikai.cniaoshop.utils.ManifestUtil;
import com.guikai.cniaoshop.utils.ToastUtils;
import com.guikai.cniaoshop.widget.ClearEditText;
import com.guikai.cniaoshop.widget.CnToolbar;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Response;

public class RegSecondActivity extends BaseActivity {

    private CnToolbar mToolbar;
    private TextView mTxtTip;
    private Button mBtnResend;
    private ClearEditText mEtCode;
    private AlertDialog mDialog;

    private String phone;
    private String pwd;
    private String countryCode;

    private CountTimerView countTimerView;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    private SMSEvenHanlder evenHanlder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_second);
        initView();
        phone = getIntent().getStringExtra("phone");
        pwd = getIntent().getStringExtra("pwd");
        countryCode = getIntent().getStringExtra("countryCode");

        String formatedPhone = "+" + countryCode + "  " + splitPhoneNum(phone);
        String text = getString(R.string.smssdk_send_mobile_detail) + formatedPhone;
        mTxtTip.setText(Html.fromHtml(text));

        CountTimerView timerView = new CountTimerView(mBtnResend);
        timerView.start();

        MobSDK.init(this, ManifestUtil.getMetaDataValue(this, "Mob-AppKey"),
                ManifestUtil.getMetaDataValue(this,"Mob-AppSecret"));

        evenHanlder = new SMSEvenHanlder();
        SMSSDK.registerEventHandler(evenHanlder);

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("正在校验验证码")
                .build();

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mTxtTip = findViewById(R.id.txtTip);
        mBtnResend = findViewById(R.id.btn_reSend);
        mEtCode = findViewById(R.id.edittxt_code);
        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode();
            }
        });
    }

    public void reSendCode(View view) {

        SMSSDK.getVerificationCode("+"+countryCode, phone);
        countTimerView = new CountTimerView(mBtnResend, R.string.smssdk_resend_identify_code);
        countTimerView.start();

        mDialog.setMessage("正在重新获取验证码");
        mDialog.show();
    }

    //分割电话号码
    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);

        return builder.toString();
    }

    private void submitCode() {

        String vCode = mEtCode.getText().toString().trim();

        if (TextUtils.isEmpty(vCode)) {
            ToastUtils.show(this, R.string.smssdk_write_identify_code);
            return;
        }

        SMSSDK.submitVerificationCode(countryCode, phone ,vCode);
        mDialog.show();
    }

    private void doReg() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("phone",phone);
        params.put("password", DESUtil.encode(Contants.DES_KEY, pwd));

        okHttpHelper.post(Contants.API.REG, params, new SpotsCallBack<LoginRespMsg<User>>(this) {
            @Override
            public void onSuccess(Call call, Response response, LoginRespMsg<User> userLoginRespMsg) {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();

                if (userLoginRespMsg.getStatus() == LoginRespMsg.STATUS_ERROR) {
                    ToastUtils.show(RegSecondActivity.this,"注册失败"+userLoginRespMsg.getMessage());
                    return;
                }
                CniaoApplication application = CniaoApplication.getmInstance();
                application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());

                startActivity(new Intent(RegSecondActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }

            @Override
            public void onTokenError(Response response, int code) {
                super.onTokenError(response, code);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(evenHanlder);
    }

    class SMSEvenHanlder extends EventHandler {
        @Override
        public void afterEvent(final int event, final int result,
                               final Object data){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mDialog != null && mDialog.isShowing())
                        mDialog.dismiss();

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {


//                              HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
//                              String country = (String) phoneMap.get("country");
//                              String phone = (String) phoneMap.get("phone");

//                            ToastUtils.show(RegSecondActivity.this,"验证成功："+phone+",country:"+country);

                            doReg();
                            mDialog.setMessage("正在提交注册信息");
                            mDialog.show();;
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
//                                ToastUtils.show(RegActivity.this, des);
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
}
