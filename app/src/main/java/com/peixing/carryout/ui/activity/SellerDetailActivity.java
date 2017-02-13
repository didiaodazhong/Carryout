package com.peixing.carryout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.peixing.carryout.R;
import com.peixing.carryout.ui.fragment.GoodsFragment;
import com.peixing.carryout.ui.fragment.JudgeFragment;
import com.peixing.carryout.ui.fragment.SellerFragment;

public class SellerDetailActivity extends BaseActivity {
    private Toolbar toolbarSeller;
    private TabLayout tabLayoutSeller;
    private ViewPager viewPagerSeller;
    //    private TextView tvTitle;
    private static final String TAG = "SellerDetailActivity";
    private FrameLayout frameSellerDetail;


    String[] titles = new String[]{"商品", "评价", "商家"};
//    private String sellerId;
    private long sellerId;
    private SellerAdapter sellerAdapter;
    //            {R.string.goods, R.string.judge, R.string.seller};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        Intent intent = getIntent();
//        sellerId = intent.getStringExtra("sid");
        sellerId =  intent.getLongExtra("sid",-1);
        String name = intent.getStringExtra("name");
        Log.i(TAG, "onCreate:SellerId " + sellerId + "----name：" + name);
        toolbarSeller = (Toolbar) findViewById(R.id.toolbar_seller);
        tabLayoutSeller = (TabLayout) findViewById(R.id.tabLayout_seller);
        viewPagerSeller = (ViewPager) findViewById(R.id.viewPager_seller);
        frameSellerDetail = (FrameLayout) findViewById(R.id.frame_seller_detail);

//        tvTitle = (TextView) findViewById(R.id.tv_title);

//        tvShop = (TextView) findViewById(R.id.tv_shop);
//        tvShop.setText(name + "--id--" + SellerId);
//        tvTitle.setText(name);
        toolbarSeller.setTitle(name);
        setSupportActionBar(toolbarSeller);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sellerAdapter = new SellerAdapter(getSupportFragmentManager());
        viewPagerSeller.setAdapter(sellerAdapter);
        tabLayoutSeller.setupWithViewPager(viewPagerSeller);
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


    private class SellerAdapter extends FragmentPagerAdapter {

        public SellerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new GoodsFragment();
                    Bundle arguments = new Bundle();
                    arguments.putLong("seller_id", sellerId);
                    fragment.setArguments(arguments);
                    break;
                case 1:
                    fragment = new JudgeFragment();
                    break;
                case 2:
                    fragment = new SellerFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
