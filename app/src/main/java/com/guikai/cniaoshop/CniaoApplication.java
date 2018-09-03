package com.guikai.cniaoshop;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

//初始化Fresco
public class CniaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
