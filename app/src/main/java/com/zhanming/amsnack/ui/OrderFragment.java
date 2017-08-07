package com.zhanming.amsnack.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.adapter.OrderFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhanming on 2017/8/7.
 */

public class OrderFragment extends android.app.Fragment {

    @BindView(R.id.tabLayout_order)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<Fragment> fragments;
    private OrderFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        fragments = new ArrayList<>();
        OrderItemFragment fragment1 = new OrderItemFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type",1);
        fragment1.setArguments(bundle1);
        OrderItemFragment fragment2 = new OrderItemFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type",2);
        fragment2.setArguments(bundle2);
        fragments.add(fragment1);
        fragments.add(fragment2);
        mAdapter = new OrderFragmentAdapter(getFragmentManager(),getActivity());
        mAdapter.setFragments(fragments);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }


}
