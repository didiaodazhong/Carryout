package com.peixing.carryout.model.net.bean;

import android.content.pm.ActivityInfo;

import java.util.ArrayList;

/**
 * Created by peixing on 2017/1/18.
 */
public class Seller {
    public long id;
    public String pic;
    public String name;

    public String score;
    public String sale;
    public String ensure;

    public String invoice;
    public int sendPrice;
    public String deliveryFee;

    public String recentVisit;
    public String distance;
    public String time;

    private ArrayList<ActivityInfo> activityList;
}
