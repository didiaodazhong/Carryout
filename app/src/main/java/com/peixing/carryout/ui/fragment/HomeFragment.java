package com.peixing.carryout.ui.fragment;


import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.dagger.component.fragment.DaggerHomeFragmentComponent;
import com.peixing.carryout.dagger.module.fragment.HomeFragmentModule;
import com.peixing.carryout.presenter.fragment.HomeFragmentPresenter;
import com.peixing.carryout.ui.activity.LocationActivity;
import com.peixing.carryout.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private CoordinatorLayout coordinator;
    private SwipeRefreshLayout mSwipeHome;
    private RecyclerView mRecyclerHome;
    private LinearLayout mLlTitle;
    private LinearLayout mLlTitleSearch;
    private LinearLayout mLlAddress;
    private TextView mTvHomeAddress;
    private LinearLayout mLlSearchText;
    private List<String> stringList;
    private HomeAdapter mHomeAdapter;
    @Inject
    HomeFragmentPresenter presenter;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this)).build().in(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coordinator = (CoordinatorLayout) view.findViewById(R.id.coordinator);
        mSwipeHome = (SwipeRefreshLayout) view.findViewById(R.id.swipe_home);
        mRecyclerHome = (RecyclerView) view.findViewById(R.id.recycler_home);
        mLlTitle = (LinearLayout) view.findViewById(R.id.ll_title);
        mLlTitleSearch = (LinearLayout) view.findViewById(R.id.ll_title_search);
        mLlAddress = (LinearLayout) view.findViewById(R.id.ll_address);
        mTvHomeAddress = (TextView) view.findViewById(R.id.tv_home_address);
        mLlSearchText = (LinearLayout) view.findViewById(R.id.ll_search_text);
        mRecyclerHome.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mLlAddress.setOnClickListener(this);
        mLlSearchText.setOnClickListener(this);
        initView();

    }


    private void initView() {
        stringList = new ArrayList<>();
//        presenter.getData();
        mHomeAdapter = new HomeAdapter(this.getContext());
        mRecyclerHome.setAdapter(mHomeAdapter);
        mRecyclerHome.addOnScrollListener(Listener);
//        mSwipeHome.setRefreshing(false);
    }

    public HomeAdapter getmHomeAdapter() {
        return mHomeAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_address:
                Snackbar.make(coordinator, "您确定要定位当前位置吗？", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(v.getContext(), "已经重新定位", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyApplication.getmContext(), LocationActivity.class);
                                startActivityForResult(intent, 200);
                            }
                        })
                        .show();
                break;
            case R.id.ll_search_text:
                Snackbar.make(coordinator, "您要搜索店铺或商品", Snackbar.LENGTH_SHORT)
                        .setAction("确定", null)
                        .show();
                break;
            default:
                break;
        }
    }

    //数值方向滑动间距
    private int sunY;
    private float duration = 350.0f;//在0-150之间去改变头部的透明度
    private ArgbEvaluator evalutor = new ArgbEvaluator();
    private RecyclerView.OnScrollListener Listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            sunY += dy;
            int bgColor = 0X553190E8;
            if (sunY < 0) {
                bgColor = 0X553190E8;
            } else if (sunY > 350) {
                bgColor = 0XFF3190E8;
            } else {
                bgColor = (int) evalutor.evaluate(sunY / duration, 0X553190E8, 0XFF3190E8);
            }
            mLlTitle.setBackgroundColor(bgColor);
        }
    };

    public void failed(String msg) {
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
//        Log.i(TAG, "onResume: 获取数据");
        mSwipeHome.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title = data.getStringExtra("title");
        mTvHomeAddress.setText(title);
    }
}
