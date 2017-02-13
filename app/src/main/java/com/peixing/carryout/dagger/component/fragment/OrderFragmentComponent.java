package com.peixing.carryout.dagger.component.fragment;

import com.peixing.carryout.dagger.module.fragment.OrderFragmentModule;
import com.peixing.carryout.ui.fragment.OrderFragment;

import dagger.Component;

/**
 * Created by peixing on 2017/2/9.
 */
@Component(modules = OrderFragmentModule.class)
public interface OrderFragmentComponent {
    void in(OrderFragment fragment);
}
