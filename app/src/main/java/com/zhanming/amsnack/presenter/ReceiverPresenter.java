package com.zhanming.amsnack.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.PresenterDelegate;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.contract.ReceiverContract;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/6.
 */

public class ReceiverPresenter extends PresenterDelegate<ReceiverContract.View> implements ReceiverContract.Presenter {

    public ReceiverPresenter(Context ctx, ReceiverContract.View v) {
        super(ctx, v);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void addAdress() {
        View root = View.inflate(mContext, R.layout.dialog_receiver_edit, null);
        final EditText et_name = (EditText) root.findViewById(R.id.et_dialog_receiverName);
        final EditText et_phone = (EditText) root.findViewById(R.id.et_dialog_receiver_phone);
        final EditText et_address = (EditText) root.findViewById(R.id.et_dialog_receiver_address);

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("添加收货人地址")
                .setView(root)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final String name = et_name.getText().toString();
                        final String phone = et_phone.getText().toString();
                        final String address = et_address.getText().toString();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
