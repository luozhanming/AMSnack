package com.zhanming.amsnack.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by zhanming on 2017/8/7.
 */

public class OrderFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private List<Fragment> fragments;

    public OrderFragmentAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.mContext = ctx;
    }

    public void setFragments(List<Fragment> fragments){
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "未处理";
        }else if(position==1){
            return "正发货";
        }else{
            return "";
        }
    }

    @Override
    public int getCount() {
        if(fragments!=null&&fragments.size()>0){
            return fragments.size();
        }else{
            return 0;
        }
    }
}
