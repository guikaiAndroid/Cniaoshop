package com.guikai.cniaoshop.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Time:         2018/8/28 23:37
 * Package_Name: com.guikai.cniaoshop.http
 * File_Name:    BaseCallback
 * Creator:      Anding
 * Note:         TODO
 */
public abstract class BaseCallback<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    //扩展callback回调方法
    public abstract void onRequestBefore(Request request);


    public abstract void onFailure(Call call, IOException e);

    public abstract  void onResponse(Response response);

    public abstract void onSuccess(Call call, Response response, T t);

    public abstract void onError(Call call, Response response, int code, Exception e);

    public abstract void onTokenError(Response response, int code);


}
