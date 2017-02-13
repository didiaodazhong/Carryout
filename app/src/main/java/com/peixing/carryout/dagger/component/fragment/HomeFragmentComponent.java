package com.peixing.carryout.dagger.component.fragment;

import com.peixing.carryout.dagger.module.fragment.HomeFragmentModule;
import com.peixing.carryout.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * /**
 * 将创建好的业务对象设置给目标
 * Created by peixing on 2017/1/18.
 */

@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void in(HomeFragment fragment);
}
