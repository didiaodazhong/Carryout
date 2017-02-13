package com.peixing.carryout.dagger.component.fragment;


import com.peixing.carryout.dagger.module.fragment.GoodsFragmentModule;
import com.peixing.carryout.ui.fragment.GoodsFragment;

import dagger.Component;

/**
 * Created by peixing.
 */
@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentComponent {
    void in(GoodsFragment fragment);
}
