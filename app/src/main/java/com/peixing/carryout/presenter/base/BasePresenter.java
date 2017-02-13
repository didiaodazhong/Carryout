package com.peixing.carryout.presenter.base;

import android.util.Log;

import com.peixing.carryout.model.net.api.ResponseInfoAPI;
import com.peixing.carryout.model.net.bean.ResponseInfo;
import com.peixing.carryout.utils.ErrorInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.peixing.carryout.utils.Constant.BASE_URL;

/**
 * Created by peixing on 2017/1/17.
 */

public abstract class BasePresenter {

    private static final String TAG = "BasePresenter";
    protected static ResponseInfoAPI responseInfo;

    public BasePresenter() {
        if (responseInfo == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            responseInfo = retrofit.create(ResponseInfoAPI.class);
        }
    }


    public class CallbackAdapter implements Callback<ResponseInfo> {

        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            if (response != null && response.isSuccessful()) {
                ResponseInfo info = response.body();
//                Log.i(TAG, "onResponse: "+info.toString());
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
            Log.i(TAG, "onFailure: ");
        }
    }

    /**
     * 错误处理
     *
     * @param msg
     */
    protected abstract void failed(String msg);

    /**
     * 解析服务器返回数据
     *
     * @param data
     */
    protected abstract void parseData(String data);
}
