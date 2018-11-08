package com.guikai.cniaoshop;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guikai.cniaoshop.adapter.WareOrderAdapter;
import com.guikai.cniaoshop.adapter.layoutmanager.FullyLinearLayoutManager;
import com.guikai.cniaoshop.bean.ShoppingCart;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.msg.CreateOrderRespMsg;
import com.guikai.cniaoshop.utils.CartProvider;
import com.guikai.cniaoshop.utils.JSONUtil;
import com.pingplusplus.android.PaymentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class CreateOrderActivity extends BaseActivity implements View.OnClickListener {

    //银联支付渠道
    private static final String CHANNEL_UPACP = "upacp";

    //微信支付渠道
    private static final String CHANNEL_WECHAT = "wx";

    //支付支付渠道
    private static final String CHANNEL_ALIPAY = "alipay";

    //百度支付渠道
    private static final String CHANNEL_BFB = "bfb";

    //京东支付渠道
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

        amount = mAdapter.getTotalPrice();
        mTxtTotal.setText("应付款： ￥"+amount);
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

    //提交订单，进行支付
    public void createNewOrder(View view) {

        final List<ShoppingCart> carts = mAdapter.getDatas();
        List<WareItem> items = new ArrayList<>(carts.size());
        for (ShoppingCart c:carts ) {

            WareItem item = new WareItem(c.getId(),c.getPrice().intValue());
            items.add(item);

        }

        String item_json = JSONUtil.toJSON(items);

        Map<String,String> params = new HashMap<>(5);
        params.put("user_id",CniaoApplication.getmInstance().getUser().getId()+"");
        params.put("item_json",item_json);
        params.put("pay_channel",payChannel);
        params.put("amount",(int)amount+"");
        params.put("addr_id",1+"");

        mBtnCreateOrder.setEnabled(false);

        okHttpHelper.post(Contants.API.ORDER_CREATE, params, new SpotsCallBack<CreateOrderRespMsg>(this) {
            @Override
            public void onSuccess(Call call, Response response, CreateOrderRespMsg respMsg) {
                mBtnCreateOrder.setEnabled(true);
                Log.e("xxxx", "打印"+respMsg.getData());
//                orderNum = respMsg.getData().getOrderNum();
//                Charge charge = respMsg.getData().getCharge();

//                openPaymentActivity(JSONUtil.toJSON(charge));

            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {
                mBtnCreateOrder.setEnabled(true);
            }
        });

    }

    private void openPaymentActivity(String charge){

        Intent intent = new Intent();
        String packageName = getPackageName();
        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, Contants.REQUEST_CODE_PAYMENT);
    }

    class WareItem {
        private Long ware_id;
        private int amount;

        public WareItem(Long ware_id, int amount) {
            this.ware_id = ware_id;
            this.amount = amount;
        }

        public Long getWare_id() {
            return ware_id;
        }

        public void setWare_id(Long ware_id) {
            this.ware_id = ware_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }


}
