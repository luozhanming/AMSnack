package com.zhanming.amsnack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhanming.amsnack.bean.AppUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                AppUser user = BmobUser.getCurrentUser(AppUser.class);
                user.setName("张三");
                user.setAge(5);
                user.setSex("男");
                user.setImg(new BmobFile());
                break;
        }
    }
}
