package com.guikai.cniaoshop.utils;

import android.content.Context;
import android.text.TextUtils;

import com.guikai.cniaoshop.Contants;
import com.guikai.cniaoshop.bean.User;

public class UserLocalData {

    public static void putUser(Context context, User user) {

        //将User对象转为json字符串
        String user_json = JSONUtil.toJSON(user);

        //然后存入sharePreference
        PreferencesUtils.putString(context, Contants.USER_JSON, user_json);
    }

    public static void putoken(Context context, String token) {

        PreferencesUtils.putString(context,Contants.TOKEN, token);

    }

    public static User getUser(Context context) {

        //逆过程 将sharePreference里json字符串读取
        String user_json = PreferencesUtils.getString(context, Contants.USER_JSON);

        if (!TextUtils.isEmpty(user_json)) {
            return JSONUtil.fromJson(user_json, User.class);
        }

        return null;
    }

    public static String getToken(Context context) {
        return PreferencesUtils.getString(context, Contants.TOKEN);
    }

    public static void clearUser(Context context) {
        PreferencesUtils.putString(context, Contants.USER_JSON, "");
    }

    public static void clearToken(Context context) {
        PreferencesUtils.putString(context, Contants.TOKEN, "");
    }

}
