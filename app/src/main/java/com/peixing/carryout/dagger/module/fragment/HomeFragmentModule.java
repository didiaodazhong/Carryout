package com.peixing.carryout.dagger.module.fragment;

import com.peixing.carryout.presenter.fragment.HomeFragmentPresenter;
import com.peixing.carryout.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by peixing on 2017/1/18.
 */
@Module
public class HomeFragmentModule {
    private HomeFragment homeFragment;

    public HomeFragmentModule(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Provides
    HomeFragmentPresenter provideHomeFragmentPresenter() {
        return new HomeFragmentPresenter(homeFragment);
    }

}
