package com.guikai.cniaoshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guikai.cniaoshop.adapter.WareOrderAdapter;
import com.guikai.cniaoshop.adapter.layoutmanager.FullyLinearLayoutManager;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.utils.CartProvider;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    private static final String CHANNEL_JDPAY_WAP = "jdpay_wap";

    private TextView textOrder;
    private RecyclerView mRecyclerView;
    private RelativeLayout mLayoutAlipay;
    private RelativeLayout mLayoutWechat;
    private RelativeLayout mLayoutBd;
    private RadioButton mRbAlipay;
    private RadioButton mRbWechat;
    private RadioButton mRbBd;
    private Button mBtnCreateOrder;
    private TextView mTxtTotal;

    private CartProvider cartProvider;
    private WareOrderAdapter mAdapter;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    private String orderNum;
    private String payChannel = CHANNEL_ALIPAY;
    private float amount;

    private HashMap<String,RadioButton> channels = new HashMap<>(3);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initView();
        showData();
    }

    private void showData() {
        cartProvider = new CartProvider(this);
        mAdapter = new WareOrderAdapter(this,cartProvider.getAll());

        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        textOrder = findViewById(R.id.txt_order);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutAlipay = findViewById(R.id.rl_alipay);
        mLayoutWechat = findViewById(R.id.rl_wechat);
        mLayoutBd = findViewById(R.id.rl_bd);
        mRbAlipay = findViewById(R.id.rb_alipay);
        mRbWechat = findViewById(R.id.rb_webchat);
        mRbBd = findViewById(R.id.rb_bd);
        mBtnCreateOrder = findViewById(R.id.btn_createOrder);
        mTxtTotal = findViewById(R.id.txt_total);

        channels.put(CHANNEL_ALIPAY,mRbAlipay);
        channels.put(CHANNEL_WECHAT,mRbWechat);
        channels.put(CHANNEL_BFB,mRbBd);

        mLayoutWechat.setOnClickListener(this);
        mLayoutAlipay.setOnClickListener(this);
        mLayoutBd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        selectPayChannle(v.getTag().toString());
    }

    public void selectPayChannle(String paychannel) {

        for (Map.Entry<String, RadioButton> entry:channels.entrySet()) {

            payChannel = paychannel;
            RadioButton rb = entry.getValue();

            if (entry.getKey().equals(paychannel)) {
                boolean isCheck = rb.isChecked();
                rb.setChecked(!isCheck);
            } else {
                rb.setChecked(false);
            }
        }
    }

}
