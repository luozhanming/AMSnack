package com.zhanming.amsnack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhanming.amsnack.R;
import com.zhanming.amsnack.base.BaseMVPActivity;
import com.zhanming.amsnack.contract.SignUpContract;
import com.zhanming.amsnack.presenter.SignUpPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SignUpActivity extends BaseMVPActivity<SignUpContract.Presenter> implements SignUpContract.View {

    @BindView(R.id.et_signUp_username)
    EditText et_username;
    @BindView(R.id.et_signUp_password)
    EditText et_password;
    @BindView(R.id.et_signUp_email)
    EditText et_email;
    @BindView(R.id.btn_signUp_submit)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signUp();
            }
        });
    }

    @Override
    public SignUpContract.Presenter createPresenter() {
        return new SignUpPresenter(this, this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_sign_up;
    }


    @Override
    public Map<String, String> getForm() {
        Map<String,String> form = new HashMap<>();
        form.put("username",et_username.getText().toString());
        form.put("password",et_password.getText().toString());
        form.put("email",et_email.getText().toString());
        return form;
    }

    @Override
    public void jump2Login() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void jump2Main() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSignUpCode(boolean isSuccessful) {
        if(isSuccessful){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }
}
