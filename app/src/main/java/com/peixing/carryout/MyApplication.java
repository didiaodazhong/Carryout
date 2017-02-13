package com.peixing.carryout;

import android.app.Application;
import android.content.Context;

import com.amap.api.services.core.LatLonPoint;

/**
 * Created by peixing on 2017/1/17.
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static LatLonPoint LOCATION = null;

    // 测试数据
    public static int USERID = 0001;
    public static String phone = "13280000000";

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }
}
