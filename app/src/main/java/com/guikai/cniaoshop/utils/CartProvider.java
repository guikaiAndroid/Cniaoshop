package com.guikai.cniaoshop.utils;


import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.guikai.cniaoshop.bean.ShoppingCart;
import com.guikai.cniaoshop.bean.Wares;

import java.util.ArrayList;
import java.util.List;

//提供购物车数据的类
public class CartProvider {

    private static final String CART_JSON = "cart_json";

    private SparseArray<ShoppingCart> datas = null;

    private Context mContext;

    public CartProvider(Context context) {
        datas = new SparseArray<>(10);
        mContext = context;
        listToSparse();
    }

    //写入数据 参数为一个商品实体类
    public void put(ShoppingCart cart) {

        //写入数据之前 我们需要判断购物车是否此商品存在 如果存在+1 否则新增商品
        ShoppingCart temp = datas.get(cart.getId().intValue());

        if (temp != null) {
            temp.setCount(temp.getCount()+1);
        } else {
            temp = cart;
            temp.setCount(1);
        }
        datas.put(cart.getId().intValue(),temp);
        commit();
    }

    public void put(Wares wares) {
        ShoppingCart cart = convertData(wares);
        put(cart);
    }

    public void update(ShoppingCart cart) {
        datas.put(cart.getId().intValue(),cart);
        commit();
    }

    //删除数据
    public void delete(ShoppingCart cart) {
        datas.delete(cart.getId().intValue());
        commit();
    }

    //删除数据
    public void deleteAll() {
        datas.clear();
        commit();
    }

    //获取所有数据
    public List<ShoppingCart> getAll() {
        return getDataFromLocal();
    }

    //从SharePreference中读取数据
    private List<ShoppingCart> getDataFromLocal() {
        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = JSONUtil.fromJson(json, new TypeToken<List<ShoppingCart>>(){}.getType());
        }
        return carts;
    }

    private void commit() {
        List<ShoppingCart> carts = sparseToList();
        PreferencesUtils.putString(mContext,CART_JSON, JSONUtil.toJSON(carts));
    }

    private List<ShoppingCart> sparseToList() {

        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }

    //将list转化为Sparese
    private void listToSparse() {
        List<ShoppingCart> carts = getDataFromLocal();

        if (carts != null && carts.size()> 0) {
            for (ShoppingCart cart :carts) {
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }

    public ShoppingCart convertData(Wares item) {
        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setPrice(item.getPrice());
        cart.setName(item.getName());

        return cart;
    }
}
