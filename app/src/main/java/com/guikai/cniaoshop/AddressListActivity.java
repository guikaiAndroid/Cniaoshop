package com.guikai.cniaoshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guikai.cniaoshop.adapter.AddressAdapter;
import com.guikai.cniaoshop.adapter.decoration.DividerItemDecortion;
import com.guikai.cniaoshop.bean.Address;
import com.guikai.cniaoshop.http.OkHttpHelper;
import com.guikai.cniaoshop.http.SpotsCallBack;
import com.guikai.cniaoshop.msg.BaseRespMsg;
import com.guikai.cniaoshop.widget.CnToolbar;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class AddressListActivity extends BaseActivity {

    private CnToolbar mToolbar;
    private RecyclerView mRecyclerView;

    private AddressAdapter mAdater;
    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        initAddress();
    }

    private void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddActivity();
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toAddActivity() {
        Intent intent = new Intent(this, AddressAddActivity.class);
        startActivityForResult(intent, Contants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        initAddress();
    }

    private void initAddress() {

        Map<String, Object> params = new HashMap<>(1);
        params.put("user_id", CniaoApplication.getmInstance().getUser().getId());

        mHttpHelper.get(Contants.API.ADDRESS_LIST, params, new SpotsCallBack<List<Address>>(this) {

            @Override
            public void onSuccess(Call call, Response response, List<Address> addresses) {
                showAddress(addresses);
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }
        });
    }

    private void showAddress(List<Address> addresses) {

        Collections.sort(addresses);
        if (mAdater == null) {
            mAdater = new AddressAdapter(this, addresses, new AddressAdapter.AddressLisneter() {
                @Override
                public void setDefault(Address address) {
                    updateAddress(address);
                }
            });
            mRecyclerView.setAdapter(mAdater);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecortion.VERTICAL_LIST));
        } else {
            mAdater.refreshData(addresses);
            mRecyclerView.setAdapter(mAdater);
        }
    }

    private void updateAddress(Address address) {
        Map<String,Object> params = new HashMap<>(1);
        params.put("id",address.getId());
        params.put("consignee",address.getConsignee());
        params.put("phone",address.getPhone());
        params.put("addr",address.getAddr());
        params.put("zip_code",address.getZipCode());
        params.put("is_default",address.getDefault());

        mHttpHelper.post(Contants.API.ADDRESS_UPDATE, params, new SpotsCallBack<BaseRespMsg>(this) {

            @Override
            public void onSuccess(Call call, Response response, BaseRespMsg baseRespMsg) {
                if(baseRespMsg.getStatus() == BaseRespMsg.STATUS_SUCCESS){

                    initAddress();
                }
            }

            @Override
            public void onError(Call call, Response response, int code, Exception e) {

            }



        });
    }

}
