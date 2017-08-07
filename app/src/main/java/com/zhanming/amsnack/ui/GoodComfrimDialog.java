package com.zhanming.amsnack.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhanming.amsnack.R;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bean.ShoppingCar;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.picasso.RoundTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/5.
 */

public class GoodComfrimDialog extends Dialog implements View.OnClickListener {

    private Good good;
    @BindView(R.id.iv_dialog_good_img)
    ImageView iv_img;
    @BindView(R.id.tv_dialog_good_name)
    TextView tv_name;
    @BindView(R.id.tv_dialog_good_price)
    TextView tv_price;
    @BindView(R.id.tv_dialog_good_totalPrice)
    TextView tv_totalPrice;
    @BindView(R.id.btn_dialog_good_add)
    ImageButton btn_add;
    @BindView(R.id.btn_dialog_good_sub)
    ImageButton btn_sub;
    @BindView(R.id.et_dialog_good_quantity)
    EditText et_quantity;
    @BindView(R.id.btn_dialog_good_comfirm)
    Button btn_comfirm;

    private int quantity = 0;

    public GoodComfrimDialog(Context context, Good good) {
        super(context);
        this.good = good;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_good);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Picasso.with(getContext()).load(good.getImgUrl()).transform(new RoundTransform()).into(iv_img);
        tv_name.setText(good.getName());
        tv_price.setText("¥" + good.getPrice());
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_comfirm.setOnClickListener(this);
        et_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    //判断是否超出库存
                    boolean b = checkStore(Integer.valueOf(s.toString()));
                    if (b) {
                        quantity = Integer.valueOf(s.toString());
                        float totalPrice = quantity * good.getPrice();
                        tv_totalPrice.setText("¥" + totalPrice);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private boolean checkStore(int count) {
        int store = good.getStore();
        if (count > store) {
            Toast.makeText(getContext(), "超出库存，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_good_add:
                if (et_quantity.getText().toString().equals("")) {
                    et_quantity.setText(0 + "");
                }
                int count1 = Integer.valueOf(et_quantity.getText().toString()) + 1;
                et_quantity.setText(count1 + "");
                break;
            case R.id.btn_dialog_good_sub:
                if (et_quantity.getText().toString().equals("")) {
                    et_quantity.setText(0 + "");
                }
                int count2 = Integer.valueOf(et_quantity.getText().toString()) - 1;
                if (count2 <= 0) {
                    et_quantity.setText(0 + "");
                } else {
                    et_quantity.setText(count2 + "");
                }
                break;
            case R.id.btn_dialog_good_comfirm:
                ShoppingBussiness.addGoodToShoppingCar(good,quantity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                dismiss();
                            }
                        });
                break;
        }
    }


}
