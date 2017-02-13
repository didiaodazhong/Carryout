package com.peixing.carryout.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.peixing.carryout.R;

public class OrderDetailActivity extends AppCompatActivity {
    private ImageView ivOrderDetailBack;
    private TextView tvSellerName;
    private TextView tvOrderDetailTime;
    private MapView map;
    private LinearLayout llOrderDetailTypeContainer;
    private LinearLayout llOrderDetailTypePointContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ivOrderDetailBack = (ImageView) findViewById(R.id.iv_order_detail_back);
        tvSellerName = (TextView) findViewById(R.id.tv_seller_name);
        tvOrderDetailTime = (TextView) findViewById(R.id.tv_order_detail_time);
        map = (MapView) findViewById(R.id.map);
        llOrderDetailTypeContainer = (LinearLayout) findViewById(R.id.ll_order_detail_type_container);
        llOrderDetailTypePointContainer = (LinearLayout) findViewById(R.id.ll_order_detail_type_point_container);

    }
}
