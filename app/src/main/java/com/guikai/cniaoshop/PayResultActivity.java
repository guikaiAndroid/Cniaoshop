package com.guikai.cniaoshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.guikai.cniaoshop.fragment.CartFragment;
import com.guikai.cniaoshop.utils.CartProvider;

import static com.mob.MobSDK.getContext;

public class PayResultActivity extends BaseActivity {

    private CartProvider cartProvider;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        cartProvider = new CartProvider(getContext());
        cartProvider.deleteAll();
    }


    @Override
    public void onBackPressed() {

    }

    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
