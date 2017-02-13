package com.peixing.carryout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.peixing.carryout.R;
import com.peixing.carryout.ui.fragment.BaseFragment;
import com.peixing.carryout.utils.FragmentFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

  /*  @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.bottom_navigation_bar_main)
    BottomNavigationBar bottomNavigationBarContainer;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;*/

    private LinearLayout activityMain;
    private FrameLayout frameMain;
    private BottomNavigationBar bottomNavigationBarContainer;

    int[] bottomItems = new int[]{R.string.home, R.string.me, R.string.more, R.string.order};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        frameMain = (FrameLayout) findViewById(R.id.frame_main);
        bottomNavigationBarContainer = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_main);
        bottomNavigationBarContainer.setMode(BottomNavigationBar.MODE_FIXED);
//        bottomNavigationBarContainer.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        initView();
        initFragment();
    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0, length = bottomItems.length; i < length; i++) {
            Fragment fragment = supportFragmentManager.findFragmentByTag(i + "");
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
        }
        fragmentTransaction.commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_main, FragmentFactory.getFragment(0), "0")
                .commit();
    }

    private void initView() {

        BottomNavigationItem homeItem = new BottomNavigationItem(R.drawable.home, R.string.home);
        BottomNavigationItem meItem = new BottomNavigationItem(R.drawable.me, R.string.me);
        BottomNavigationItem moreItem = new BottomNavigationItem(R.drawable.more, R.string.more);
        BottomNavigationItem orderItem = new BottomNavigationItem(R.drawable.order, R.string.order);
        bottomNavigationBarContainer.addItem(homeItem)
                .addItem(meItem)
                .addItem(moreItem)
                .addItem(orderItem)
                .initialise();
        bottomNavigationBarContainer.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            transaction.add(R.id.frame_main, fragment);
        }
        transaction.show(fragment).commit();
    }

    @Override
    public void onTabUnselected(int position) {
        getSupportFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position)).commit();
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentFactory.getFragment(0).onActivityResult(requestCode, resultCode, data);
    }
}
