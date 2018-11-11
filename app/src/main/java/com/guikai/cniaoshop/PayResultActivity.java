package com.guikai.cniaoshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PayResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
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
