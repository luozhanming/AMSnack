package com.zhanming.amsnack.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cdc4512 on 2017/7/19.
 */

public abstract class BaseMVPActivity<T extends BasePresenter> extends AppCompatActivity implements IMVPView {

    protected T mPresenter;
    protected Unbinder unbinder;
    private PresenterDelegate delegate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        unbinder = ButterKnife.bind(this);
        mPresenter = (T) createPresenter();
        if (mPresenter instanceof PresenterDelegate) {
            delegate = (PresenterDelegate) mPresenter;
        } else {
            throw new IllegalArgumentException("Presenter must extends PresenterDelegate");
        }
        preCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        postCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
        unbinder.unbind();
        mPresenter = null;
        delegate = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        delegate.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        delegate.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        delegate.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        delegate.onSave(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        delegate.onRestore(savedInstanceState);
    }

    public abstract T createPresenter();

    public void preCreate(@Nullable Bundle savedInstance){

    }

    public void postCreate(@Nullable Bundle saveInstance){

    }
}