package com.zhanming.amsnack.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.BaseMVPFragment;
import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bean.Receiver;
import com.zhanming.amsnack.bussiness.UserBussiness;
import com.zhanming.amsnack.contract.MineContract;
import com.zhanming.amsnack.picasso.CircleTransform;
import com.zhanming.amsnack.presenter.MinePresenter;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/5.
 */

public class MineFragment extends BaseMVPFragment<MineContract.Presenter> implements MineContract.View, View.OnClickListener {

    @BindView(R.id.iv_mine_touxiang)
    ImageView iv_touxiang;
    @BindView(R.id.rl_mine_age)
    View rl_age;
    @BindView(R.id.rl_mine_logout)
    View rl_logout;
    @BindView(R.id.rl_mine_name)
    View rl_name;
    @BindView(R.id.rl_mine_receiver)
    View rl_receiver;
    @BindView(R.id.tv_mine_age)
    TextView tv_age;
    @BindView(R.id.tv_mine_credit)
    TextView tv_credit;
    @BindView(R.id.tv_mine_name)
    TextView tv_name;
    @BindView(R.id.tv_mine_sex)
    TextView tv_sex;
    @BindView(R.id.tv_mine_username)
    TextView tv_username;
    @BindView(R.id.rl_mine_sex)
    View rl_sex;

    @Override
    public MineContract.Presenter createPresenter() {
        return new MinePresenter(getActivity(), this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetInfo();

    }

    private void resetInfo() {
        AppUser user = AppUser.getCurrentUser(AppUser.class);
        tv_username.setText(user.getUsername() + "");
        tv_age.setText(user.getAge() + "");
        tv_credit.setText(user.getCredit() + "");
        tv_sex.setText(user.getSex() + "");
        tv_name.setText(user.getName() + "");
        rl_age.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_logout.setOnClickListener(this);
        rl_receiver.setOnClickListener(this);
        Picasso.with(getActivity()).load(R.mipmap.man).transform(new CircleTransform()).into(iv_touxiang);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine_touxiang:

                break;
            case R.id.rl_mine_age:
                final EditText editText = new EditText(getActivity());
                editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("年龄")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s = editText.getText().toString();
                                final int age = Integer.valueOf(s);
                                UserBussiness.modifyUserInformation("", age, "", 0)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Void>() {
                                            @Override
                                            public void call(Void aVoid) {
                                                tv_age.setText(age + "");
                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.rl_mine_logout:
                BmobUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.rl_mine_receiver:
                jump2ReceiverDetail();
                break;
            case R.id.rl_mine_sex:
                final EditText editText1 = new EditText(getActivity());
                editText1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                AlertDialog dialog1 = new AlertDialog.Builder(getActivity())
                        .setTitle("性别")
                        .setItems(new String[]{"男", "女"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sex = null;
                                if (which == 0) {
                                    sex = "男";
                                    tv_sex.setText(sex);
                                } else if (which == 1) {
                                    sex = "女";
                                    tv_sex.setText(sex);
                                }
                                UserBussiness.modifyUserInformation("", 0, sex, 0)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Void>() {
                                            @Override
                                            public void call(Void aVoid) {

                                            }
                                        });
                            }
                        }).show();
                break;
            case R.id.rl_mine_name:
                final EditText editText2 = new EditText(getActivity());
                editText2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                AlertDialog dialog2 = new AlertDialog.Builder(getActivity())
                        .setTitle("昵称")
                        .setView(editText2)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String s = editText2.getText().toString();
                                UserBussiness.modifyUserInformation(s, 0, "", 0)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Void>() {
                                            @Override
                                            public void call(Void aVoid) {
                                                tv_name.setText(s);
                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }

    }

    @Override
    public void jump2ReceiverDetail() {
        Intent intent = new Intent(getActivity(), Receiver.class);
        startActivity(intent);
    }
}
