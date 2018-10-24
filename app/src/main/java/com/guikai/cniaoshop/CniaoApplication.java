package com.guikai.cniaoshop;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.guikai.cniaoshop.bean.User;
import com.guikai.cniaoshop.utils.UserLocalData;

//初始化Fresco
public class CniaoApplication extends Application {

    private User user;

    private Intent intent;

    private static CniaoApplication mInstance;

    public static CniaoApplication getmInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        initUser();

        Fresco.initialize(this);
    }

    private void initUser() {

        this.user = UserLocalData.getUser(this);

    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return UserLocalData.getToken(this);
    }

    public void putUser(User user, String token) {
        this.user = user;
        UserLocalData.putUser(this,user);
        UserLocalData.putoken(this,token);
    }

    public void clearUser() {
        this.user = null;
        UserLocalData.clearUser(this);
        UserLocalData.clearToken(this);
    }

    public void putIntent(Intent intent){
        this.intent = intent;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void jumpToTargetActivity(Context context) {
        context.startActivity(intent);
        this.intent = null;
    }



}
