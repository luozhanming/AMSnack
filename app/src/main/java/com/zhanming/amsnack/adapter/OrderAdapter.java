package com.zhanming.amsnack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.bean.Order;
import com.zhanming.amsnack.bean.Receiver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhanming on 2017/8/7.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {

    private Context mContext;
    private List<Order> orders;

    public OrderAdapter(Context ctx){
        this.mContext = ctx;
    }

    public void setOrders(List<Order> datas){
        this.orders = datas;
        notifyDataSetChanged();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Order order = orders.get(position);
        Receiver receiver = order.getReceiver();
        holder.tv_address.setText("收货人地址："+receiver.getAddress());
        holder.tv_number.setText("订单编号："+order.getObjectId());
        holder.tv_price.setText("¥"+order.getTotalPrice()+"");
        holder.tv_receiverName.setText("收货人姓名："+receiver.getReceiverName());
        holder.tv_time.setText("下单时间："+order.getCreatedAt());
        holder.tv_phone.setText("手机号："+receiver.getPhone());


    }

    @Override
    public int getItemCount() {
        if(orders!=null&&orders.size()>0){
            return orders.size();
        }else{
            return 0;
        }
    }

    class VH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_order_number)
        TextView tv_number;
        @BindView(R.id.tv_order_time)
        TextView tv_time;
        @BindView(R.id.tv_order_totalPrice)
        TextView tv_price;
        @BindView(R.id.tv_order_receiverName)
        TextView tv_receiverName;
        @BindView(R.id.tv_order_address)
        TextView tv_address;
        @BindView(R.id.tv_order_phone)
        TextView tv_phone;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
