package com.peixing.carryout.dagger.module.fragment;


import com.peixing.carryout.presenter.fragment.GoodsFragmentPresenter;
import com.peixing.carryout.ui.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by peixing.
 */
@Module
public class GoodsFragmentModule {
    private GoodsFragment fragment;

    public GoodsFragmentModule(GoodsFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public GoodsFragmentPresenter provideGoodsFragmentPresenter() {
        return new GoodsFragmentPresenter(fragment);
    }
}
