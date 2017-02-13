package com.peixing.carryout.model.net.api;

import com.peixing.carryout.model.net.bean.ResponseInfo;
import com.peixing.carryout.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by peixing on 2017/1/17.
 */

public interface ResponseInfoAPI {
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(
            @Query("username")
                    String username,
            @Query("password")
                    String password
    );

    /**
     * 获取首页数据
     *
     * @return
     */
    @GET(Constant.HOME)
    Call<ResponseInfo> home();

    /**
     * 获取商家商品详情
     *
     * @param sellerId
     * @return
     */
    @GET(Constant.GOODS)
    Call<ResponseInfo> goods(
            @Query("sellerId") long sellerId
    );
    @GET(Constant.ORDER)
    Call<ResponseInfo> orderList(@Query("userId") int userid);
}
