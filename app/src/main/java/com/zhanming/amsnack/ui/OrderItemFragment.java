package com.zhanming.amsnack.ui;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.adapter.OrderAdapter;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/7.
 */

public class OrderItemFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private int type;
    private OrderAdapter mAdapter;
    private Unbinder unbinder;

    public OrderItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_content,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        Bundle arguments = getArguments();
        type = (int) arguments.get("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mAdapter = new OrderAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        setDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mAdapter = null;

    }

    private void setDatas() {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<Order>> observable = null;
        if(type==1){
            observable = ShoppingBussiness.queryOrder(false);
        }else if(type==2){
            observable = ShoppingBussiness.queryOrder(true);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Order>>() {
                    @Override
                    public void call(List<Order> orders) {
                        mAdapter.setOrders(orders);
                        progressBar.setVisibility(View.GONE);
                    }
                });
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
