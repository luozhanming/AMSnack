package com.zhanming.amsnack.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.adapter.ReceiverAdapter;
import com.zhanming.amsnack.base.BaseMVPActivity;
import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.contract.ReceiverContract;
import com.zhanming.amsnack.presenter.ReceiverPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhanming on 2017/8/6.
 */

public class ReceiverActivity extends BaseMVPActivity<ReceiverContract.Presenter> implements ReceiverContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_receiver_detail_addAdress)
    TextView tv_addAddress;

    private ReceiverAdapter mAdapter;



    @Override
    public ReceiverContract.Presenter createPresenter() {
        return new ReceiverPresenter(this, this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_receiver;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void preCreate(@Nullable Bundle savedInstance) {
        initRecycler();
        tv_addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addAdress();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ReceiverAdapter(this);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setReceivers(List<Receiver> datas) {
        mAdapter.setReceivers(datas);
    }

    @Override
    public void hideLoading() {

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
