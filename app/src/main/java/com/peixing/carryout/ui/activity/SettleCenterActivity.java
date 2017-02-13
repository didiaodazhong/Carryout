package com.peixing.carryout.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.model.net.bean.GoodsInfo;
import com.peixing.carryout.ui.adapter.SettleCenterAdapter;
import com.peixing.carryout.utils.ShoppingCartManager;

import java.util.concurrent.CopyOnWriteArrayList;

public class SettleCenterActivity extends BaseActivity {
    private FrameLayout activitySettleCenter;
    private Toolbar toolbarSettleCenter;
    private RelativeLayout rlAddress;
    private TextView tvAddressSettle;
    private LinearLayout llPayment;
    private TextView tvPayment;
    private LinearLayout llHongbao;
    private TextView tvHongbao;
    private LinearLayout llTickerShop;
    private TextView tvShopTicket;
    private RecyclerView recyclerSettleCenter;
    private TextView tvTotalOrder;
    private Button btSubmitOrder;
    private LinearLayout llGoodsSelected;
    private SettleCenterAdapter centerAdapter;
    private TextView tvName;
    private TextView tvCount;
    private TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_center);
        toolbarSettleCenter = (Toolbar) findViewById(R.id.toolbar_settle_center);
        activitySettleCenter = (FrameLayout) findViewById(R.id.activity_settle_center);
        rlAddress = (RelativeLayout) findViewById(R.id.rl_address);
        tvAddressSettle = (TextView) findViewById(R.id.tv_address_settle);
        llPayment = (LinearLayout) findViewById(R.id.ll_payment);
        tvPayment = (TextView) findViewById(R.id.tv_payment);
        llHongbao = (LinearLayout) findViewById(R.id.ll_hongbao);
        tvHongbao = (TextView) findViewById(R.id.tv_hongbao);
        llTickerShop = (LinearLayout) findViewById(R.id.ll_ticker_shop);
        tvShopTicket = (TextView) findViewById(R.id.tv_shop_ticket);
        recyclerSettleCenter = (RecyclerView) findViewById(R.id.recycler_settle_center);

        tvTotalOrder = (TextView) findViewById(R.id.tv_total_order);
        btSubmitOrder = (Button) findViewById(R.id.bt_submit_order);
        llGoodsSelected = (LinearLayout) findViewById(R.id.ll_goods_selected);
        //设置toolbar标题栏
        setSupportActionBar(toolbarSettleCenter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerSettleCenter.setLayoutManager(new LinearLayoutManager(MyApplication.getmContext(), LinearLayoutManager.VERTICAL, false));
        //设置总金额
        Integer totalNum = ShoppingCartManager.getInstance().getMoney();
        tvTotalOrder.setText("共计：" + totalNum / 100 + "." + totalNum % 100 + "元");
//        centerAdapter = new SettleCenterAdapter();
//        recyclerSettleCenter.setAdapter(centerAdapter);

        initGoods();

    }

    /**
     * 添加用户选择商品信息到界面
     */
    private void initGoods() {
        //添加用户选择商品信息到界面
        CopyOnWriteArrayList<GoodsInfo> goodsInfos = ShoppingCartManager.getInstance().goodsInfos;
        for (GoodsInfo item : goodsInfos
                ) {
            View v = View.inflate(this, R.layout.item_settle_center_goods, null);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvCount = (TextView) v.findViewById(R.id.tv_count);
            tvPrice = (TextView) v.findViewById(R.id.tv_price);
            tvName.setText(item.name);
            tvCount.setText("x" + item.count);
            tvPrice.setText("$" + item.newPrice);
            int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            llGoodsSelected.addView(v);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
