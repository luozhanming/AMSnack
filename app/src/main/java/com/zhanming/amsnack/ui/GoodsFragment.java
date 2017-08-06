package com.zhanming.amsnack.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.adapter.GoodAdapter;
import com.zhanming.amsnack.base.BaseMVPFragment;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.contract.GoodsContract;
import com.zhanming.amsnack.presenter.GoodsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhanming on 2017/8/4.
 */

public class GoodsFragment extends BaseMVPFragment<GoodsContract.Presenter> implements GoodsContract.View {


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.prg_goods_loading)
    ProgressBar progressBar;
    private GoodAdapter mGoodAdapter;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_goods;
    }

    @Override
    public GoodsContract.Presenter createPresenter() {
        return new GoodsPresenter(getActivity(), this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initBanner();
    }

    private void initBanner() {

    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(24));
        mGoodAdapter = new GoodAdapter(getActivity());
        mRecyclerView.setAdapter(mGoodAdapter);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadHotGood(List<Good> goods) {
        List<String> titles = new ArrayList<>();
        List<String> imgUrl = new ArrayList<>();
        for (Good good : goods) {
            titles.add(good.getName());
            titles.add(good.getImgUrl());
        }
    }

    @Override
    public void loadNormalGood(List<Good> goods) {
        mGoodAdapter.setGoods(goods);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

}
