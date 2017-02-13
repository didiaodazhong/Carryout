package com.peixing.carryout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.ui.adapter.MyCartAdapter;
import com.peixing.carryout.ui.view.RecycleViewDivider;
import com.peixing.carryout.utils.ShoppingCartManager;

public class CartActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout activityCart;
    private Toolbar toolbarCart;
    private RecyclerView recyclerCort;
    private TextView tvTotalCart;
    private Button btSubmitCart;
    private MyCartAdapter cartAdapter;
    private int totalNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        activityCart = (FrameLayout) findViewById(R.id.activity_cart);
        toolbarCart = (Toolbar) findViewById(R.id.toolbar_cart);
        recyclerCort = (RecyclerView) findViewById(R.id.recycler_cort);
        tvTotalCart = (TextView) findViewById(R.id.tv_total_cart);
        btSubmitCart = (Button) findViewById(R.id.bt_submit_cart);
        //设置toolbar
        toolbarCart.setTitle("购物车");
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerCort.setLayoutManager(new LinearLayoutManager(MyApplication.getmContext(), LinearLayoutManager.VERTICAL, false));
        recyclerCort.addItemDecoration(new RecycleViewDivider(MyApplication.getmContext(), LinearLayoutManager.HORIZONTAL, 1, 0XE3E0DC));
        cartAdapter = new MyCartAdapter();
        recyclerCort.setAdapter(cartAdapter);
        totalNum = ShoppingCartManager.getInstance().getMoney();
        tvTotalCart.setText("共计：" + totalNum / 100 + "." + totalNum % 100 + "元");
        btSubmitCart.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit_cart:
                if (totalNum != 0) {
                    Snackbar.make(activityCart, R.string.tijiao, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.confirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent;
                                    if (MyApplication.USERID != 0) {
                                        intent = new Intent(CartActivity.this, SettleCenterActivity.class);
                                    } else {
                                        intent = new Intent(CartActivity.this, OrderActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                }
                break;
            default:

                break;
        }
    }
}
