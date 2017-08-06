package com.zhanming.amsnack.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/6.
 */

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.VH> {

    private Context mContext;
    private List<Receiver> receivers;


    public ReceiverAdapter(Context ctx) {

    }

    public void setReceivers(List<Receiver> datas) {
        this.receivers = datas;
        notifyDataSetChanged();
    }

    public void addReceiver(Receiver receiver) {
        if (receivers != null) {
            receivers.add(receiver);
        } else {
            receivers = new ArrayList<>();
            receivers.add(receiver);
        }
        notifyItemInserted(receivers.size() - 1);
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_receiver, null);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final Receiver receiver = receivers.get(position);
        holder.tv_name.setText(receiver.getReceiverName());
        holder.tv_address.setText(receiver.getAddress());
        holder.tv_phone.setText(receiver.getPhone());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingBussiness.deleteReceiver(receiver)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                receivers.remove(receiver);
                                notifyDataSetChanged();
                            }
                        });
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View root = View.inflate(mContext, R.layout.dialog_receiver_edit, null);
                final EditText et_name = (EditText) root.findViewById(R.id.et_dialog_receiverName);
                et_name.setText(receiver.getReceiverName());
                final EditText et_phone = (EditText) root.findViewById(R.id.et_dialog_receiver_phone);
                et_phone.setText(receiver.getPhone());
                final EditText et_address = (EditText) root.findViewById(R.id.et_dialog_receiver_address);
                et_address.setText(receiver.getAddress());

                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("编辑收货人地址")
                        .setView(root)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                final String name = et_name.getText().toString();
                                receiver.setReceiverName(name);
                                final String phone = et_phone.getText().toString();
                                receiver.setPhone(phone);
                                final String address = et_address.getText().toString();
                                receiver.setAddress(et_address.getText().toString());
                                ShoppingBussiness.modifyReceiver(receiver)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Void>() {
                                            @Override
                                            public void call(Void aVoid) {
                                                holder.tv_name.setText(name);
                                                holder.tv_phone.setText(phone);
                                                holder.tv_address.setText(address);
                                                dialog.dismiss();
                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        if (receivers != null && receivers.size() > 0) {
            return receivers.size();
        } else {
            return 0;
        }
    }

    class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_receiver_setDefault)
        CheckBox cb_setDefault;
        @BindView(R.id.tv_receiver_address)
        TextView tv_address;
        @BindView(R.id.tv_receiver_delete)
        TextView tv_delete;
        @BindView(R.id.tv_receiver_edit)
        TextView tv_edit;
        @BindView(R.id.tv_receiver_phone)
        TextView tv_phone;
        @BindView(R.id.tv_receiver_name)
        TextView tv_name;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
