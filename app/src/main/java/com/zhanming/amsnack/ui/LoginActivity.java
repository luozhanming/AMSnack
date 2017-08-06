package com.zhanming.amsnack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.BaseMVPActivity;
import com.zhanming.amsnack.contract.LoginContract;
import com.zhanming.amsnack.presenter.LoginPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseMVPActivity<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.et_login_username)
    EditText et_username;
    @BindView(R.id.et_login_password)
    EditText et_password;
    @BindView(R.id.btn_login_submit)
    Button btn_submit;
    @BindView(R.id.tv_login_signUp)
    TextView tv_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_submit.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public Map<String, String> getForm() {
        Map<String, String> form = new HashMap<>();
        form.put("username", et_username.getText().toString());
        form.put("password", et_password.getText().toString());
        return form;
    }

    @Override
    public void showLoginMessage(boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jump2Main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void jump2SignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_submit:
                mPresenter.login();
                break;
            case R.id.tv_login_signUp:
                jump2SignUp();
                break;
        }
    }
}
