package com.peixing.carryout.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peixing.carryout.MyApplication;
import com.peixing.carryout.R;
import com.peixing.carryout.dagger.component.fragment.DaggerOrderFragmentComponent;
import com.peixing.carryout.dagger.module.fragment.OrderFragmentModule;
import com.peixing.carryout.model.net.bean.Order;
import com.peixing.carryout.presenter.fragment.OrderFragmentPresenter;
import com.peixing.carryout.ui.adapter.OrderRecyclerViewAdapter;
import com.peixing.carryout.ui.view.IView;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment implements IView {
    @Inject
    OrderFragmentPresenter presenter;
    private Toolbar toolbarOrder;
    private RecyclerView recyclerView;
    private OrderRecyclerViewAdapter adapter;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerOrderFragmentComponent.builder().orderFragmentModule(new OrderFragmentModule(this)).build().in(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarOrder = (Toolbar) view.findViewById(R.id.toolbar_order);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getmContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new OrderRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
    }

    @Override
    public void success(Object o) {
        if (o instanceof List) {
            adapter.setOrders((List<Order>) o);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failed(String msg) {

    }
}
