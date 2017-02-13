package com.peixing.carryout.dagger.module.fragment;

import com.peixing.carryout.presenter.fragment.OrderFragmentPresenter;
import com.peixing.carryout.ui.fragment.OrderFragment;
import com.peixing.carryout.ui.view.IView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by peixing on 2017/2/9.
 */
@Module
public class OrderFragmentModule {
    private IView view;

    public OrderFragmentModule(IView view) {
        this.view = view;
    }

    @Provides
   public OrderFragmentPresenter provideOrderFragmentPresenter() {
        return new OrderFragmentPresenter(view);
    }
}
