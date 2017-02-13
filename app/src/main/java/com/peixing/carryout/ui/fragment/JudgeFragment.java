package com.peixing.carryout.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peixing.carryout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgeFragment extends BaseFragment {


    public JudgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_judge, container, false);
    }

}
