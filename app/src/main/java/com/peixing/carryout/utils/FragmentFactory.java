package com.peixing.carryout.utils;


import com.peixing.carryout.ui.fragment.BaseFragment;
import com.peixing.carryout.ui.fragment.HomeFragment;
import com.peixing.carryout.ui.fragment.MeFragment;
import com.peixing.carryout.ui.fragment.MoreFragment;
import com.peixing.carryout.ui.fragment.OrderFragment;

/**
 * 作者： peixing
 * 时间：2016-10-17 10:42
 */

public class FragmentFactory {

    private static HomeFragment mHomeFragment;
    private static MeFragment mMeFragment;
    private static MoreFragment mMoreFragment;
    private static OrderFragment mOrderFragment;

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                baseFragment = mHomeFragment;
                break;
            case 1:
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                }
                baseFragment = mMeFragment;
                break;
            case 2:
                if (mMoreFragment == null) {
                    mMoreFragment = new MoreFragment();
                }
                baseFragment = mMoreFragment;
                break;
            case 3:
                if (mOrderFragment == null) {
                    mOrderFragment = new OrderFragment();
                }
                baseFragment = mOrderFragment;
                break;
        }
        return baseFragment;
    }
}
