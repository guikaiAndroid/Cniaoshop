package com.guikai.cniaoshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.guikai.cniaoshop.MainActivity;
import com.guikai.cniaoshop.R;
import com.guikai.cniaoshop.adapter.CartAdapter;
import com.guikai.cniaoshop.adapter.decoration.DividerItemDecortion;
import com.guikai.cniaoshop.bean.ShoppingCart;
import com.guikai.cniaoshop.utils.CartProvider;
import com.guikai.cniaoshop.widget.CnToolbar;

import java.util.List;

public class CartFragment extends Fragment implements View.OnClickListener{

    public static final int ACTION_EDIT=1;
    public static final int ACTION_CAMPLATE=2;

    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private TextView mTextTotal;
    private Button mBtnOrder;
    private Button mBtnDel;
    private CnToolbar mToolbar;

    private CartAdapter mAdapter;
    private CartProvider cartProvider;

    public CartProvider getCartProvider() {
        return cartProvider;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartProvider = new CartProvider(getContext());
        initView(view);
        showData();

        return view;
    }


    public void refData(){
        mAdapter.clear();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
    }

    private void showData(){

        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter = new CartAdapter(getContext(),carts,mCheckBox,mTextTotal);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecortion.VERTICAL_LIST));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){

            MainActivity activity = (MainActivity) context;

            mToolbar = (CnToolbar) activity.findViewById(R.id.toolbar);

            changeToolbar();
        }
    }

    public void changeToolbar(){

        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");

        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);

    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mCheckBox = (CheckBox) view.findViewById(R.id.checkbox_all);
        mTextTotal = (TextView) view.findViewById(R.id.txt_total);
        mBtnOrder = (Button) view.findViewById(R.id.btn_order);
        mBtnDel = (Button) view.findViewById(R.id.btn_del);

        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.delCart();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int action = (int) view.getTag();
        if(ACTION_EDIT == action){

            showDelControl();
        }
        else if(ACTION_CAMPLATE == action){

            hideDelControl();
        }
    }

    private void showDelControl(){
        mToolbar.getRightButton().setText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);

        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);

    }

    private void  hideDelControl(){

        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);


        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);

        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();

        mCheckBox.setChecked(true);
    }
}


