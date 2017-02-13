package com.peixing.carryout.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.peixing.carryout.ui.view.IView;

/**
 * Created by peixing on 2017/1/18.
 */

public class BaseActivity extends AppCompatActivity implements IView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DaggerCommon
    }

    @Override
    public void success(Object o) {

    }

    @Override
    public void failed(String msg) {

    }
}
