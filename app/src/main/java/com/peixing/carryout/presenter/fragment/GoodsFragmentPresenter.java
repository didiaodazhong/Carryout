package com.peixing.carryout.presenter.fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peixing.carryout.model.net.bean.GoodsTypeInfo;
import com.peixing.carryout.model.net.bean.ResponseInfo;
import com.peixing.carryout.presenter.base.BasePresenter;
import com.peixing.carryout.ui.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by peixing on 2017/2/3.
 */

public class GoodsFragmentPresenter extends BasePresenter {
    GoodsFragment fragment;

    public GoodsFragmentPresenter(GoodsFragment goodsFragment) {
        this.fragment = goodsFragment;
    }

    public void getData(long sellerId) {
        Call<ResponseInfo> goods = responseInfo.goods(sellerId);
        goods.enqueue(new CallbackAdapter());

    }

    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parseData(String data) {
        Gson gson = new Gson();
        ArrayList<GoodsTypeInfo> goodsTypeInfos = gson.fromJson(data, new TypeToken<List<GoodsTypeInfo>>() {
        }.getType());
        fragment.success(goodsTypeInfos);

    }
}
