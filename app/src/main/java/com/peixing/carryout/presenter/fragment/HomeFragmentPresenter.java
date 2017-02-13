package com.peixing.carryout.presenter.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.peixing.carryout.model.net.bean.HomeInfo;
import com.peixing.carryout.model.net.bean.ResponseInfo;
import com.peixing.carryout.presenter.base.BasePresenter;
import com.peixing.carryout.ui.fragment.HomeFragment;
import com.peixing.carryout.utils.ErrorInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by peixing on 2017/1/18.
 */

public class HomeFragmentPresenter extends BasePresenter {

    private static final String TAG = "HomeFragmentPresenter";
    private HomeFragment homeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    /**
     * 获取主页网络数据
     */
    public void getData() {
        Call<ResponseInfo> call = responseInfo.home();
        Log.i(TAG, "getData: " + responseInfo.toString());
//        call.enqueue(new CallbackAdapter());
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                Log.i(TAG, "onResponse: " + response.toString());
                if (response != null && response.isSuccessful()) {
                    ResponseInfo info = response.body();
//                    Log.i(TAG, "onResponse: " + info.toString());
                    if ("0".equals(info.getCode())) {
                        parseData(info.getData());
                    } else {
                        String msg = ErrorInfo.INFO.get(info.getCode());
                        failed(msg);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void failed(String msg) {
        homeFragment.failed(msg);
    }

    @Override
    protected void parseData(String data) {
        Gson gson = new Gson();
        HomeInfo info = gson.fromJson(data, HomeInfo.class);
        homeFragment.getmHomeAdapter().setData(info);
    }
}
