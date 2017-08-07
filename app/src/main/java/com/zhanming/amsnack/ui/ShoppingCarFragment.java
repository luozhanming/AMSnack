package com.zhanming.amsnack.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.adapter.GoodInCarAdapter;
import com.zhanming.amsnack.base.BaseMVPFragment;
import com.zhanming.amsnack.bean.CarGoodCount;
import com.zhanming.amsnack.contract.ShoppingCarContract;
import com.zhanming.amsnack.presenter.ShoppingCarPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhanming on 2017/8/6.
 */

public class ShoppingCarFragment extends BaseMVPFragment<ShoppingCarContract.Presenter> implements ShoppingCarContract.View {

    @BindView(R.id.recyclerView_shoppingCar)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_shoppingcar_cancelCar)
    Button btn_cancelCar;
    @BindView(R.id.btn_shoppingcar_setOrder)
    Button btn_setOrder;

    private GoodInCarAdapter mAdapter;


    @Override
    public void preCreate(Bundle savedInstanceState) {
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        btn_cancelCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancelCar();
            }
        });
        btn_setOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.commitCar();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mAdapter = new GoodInCarAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public ShoppingCarContract.Presenter createPresenter() {
        return new ShoppingCarPresenter(getActivity(), this);
    }


    @Override
    public int getLayoutID() {
        return R.layout.fragment_shoppingcar;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void clearDatas() {
        mAdapter.clearDatas();
    }

    @Override
    public void showNoReceiverAlert() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("请设置默认地址")
                .setMessage("我的→我的收货地址→新增收货地址/设为默认").show();
    }

    @Override
    public void setDatas(List<CarGoodCount> datas) {
        mAdapter.setCounters(datas);
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
