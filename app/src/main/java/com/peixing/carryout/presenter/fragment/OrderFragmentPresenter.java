package com.peixing.carryout.presenter.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peixing.carryout.MyApplication;
import com.peixing.carryout.model.net.bean.Order;
import com.peixing.carryout.model.net.bean.ResponseInfo;
import com.peixing.carryout.presenter.base.BasePresenter;
import com.peixing.carryout.ui.view.IView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by peixing on 2017/2/9.
 */

public class OrderFragmentPresenter extends BasePresenter {
    private static final String TAG = "OrderFragmentPresenter";
    IView view;
    int operation = 0;// 操作的标识

    public OrderFragmentPresenter(IView view) {
        this.view = view;
    }

    @Override
    protected void failed(String msg) {
        Log.i(TAG, "failed: ");
    }

    @Override
    protected void parseData(String data) {
        Log.i(TAG, "order: "+data.toString());
        switch (operation) {
            case 1:
                view.success(data);
                break;
            case 2:
                Gson gson = new Gson();
                List<Order> orders = gson.fromJson(data, new TypeToken<List<Order>>() {
                }.getType());
                view.success(orders);
                break;
        }
    }


    public void getData() {
        operation = 2;
        Call<ResponseInfo> call = responseInfo.orderList(MyApplication.USERID);
        call.enqueue(new CallbackAdapter());
    }
}
