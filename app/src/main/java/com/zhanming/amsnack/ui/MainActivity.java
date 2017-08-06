package com.zhanming.amsnack.ui;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.BaseMVPActivity;
import com.zhanming.amsnack.contract.MainContract;
import com.zhanming.amsnack.presenter.MainPresenter;

import butterknife.BindView;

public class MainActivity extends BaseMVPActivity<MainContract.Presenter> implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bnb_main)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.iv_main_logo)
    ImageView iv_logo;

    private GoodsFragment mGoodsFragment;
    private MineFragment mMineFragment;

    private TextBadgeItem shoppingCarBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initFragment();
        initBnb();
    }

    private void initFragment() {
        mGoodsFragment = new GoodsFragment();
        mMineFragment = new MineFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initBnb() {
        shoppingCarBadgeItem = new TextBadgeItem();
        shoppingCarBadgeItem.setHideOnSelect(true)
                .setBackgroundColor(Color.RED)
                .setGravity(Gravity.TOP | Gravity.RIGHT);
        changeBadgeItemNumber(0);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.goods, "商品"))
                .addItem(new BottomNavigationItem(R.mipmap.shoppingcar, "购物车").setBadgeItem(shoppingCarBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.orders, "订单"))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "我的"))
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        mBottomNavigationBar.selectTab(0);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onTabSelected(int position) {
        changeFragement(position);
    }

    private void changeFragement(int position) {
        switch (position) {
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.container, mGoodsFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.container, mMineFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void changeBadgeItemNumber(int num) {
        if (num == 0) {
            shoppingCarBadgeItem.hide();
        } else {
            shoppingCarBadgeItem.setText(String.valueOf(num));
        }
    }

    @Override
    public void toggleBadgeItem(boolean isHide) {
        if (isHide) {
            shoppingCarBadgeItem.hide();
        } else {
            shoppingCarBadgeItem.show();
        }
    }



}
