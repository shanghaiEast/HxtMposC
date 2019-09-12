package com.haoxt.mpos.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.LoginActivity;
import com.haoxt.mpos.activity.MainTabActivity;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.common.FragmentTag;

import tft.mpos.library.base.BaseFragment;

public class LoginFragment extends BaseFragment implements View.OnClickListener {


    private TextView mRegisterTv;
    private EditText mNumberEt;
    private EditText mPasswordEt;
    private TextView mForgetTv;
    private Button mLoginBt;
    private CheckBox mShowPasswordCb;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_login);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    @Override
    public void initView() {
        mNumberEt = view.findViewById(R.id.number_et);
        mPasswordEt = view.findViewById(R.id.password_et);
        mForgetTv = view.findViewById(R.id.forget_tv);
        mLoginBt = view.findViewById(R.id.login_bt);
        mRegisterTv = view.findViewById(R.id.register_tv);
        mShowPasswordCb = view.findViewById(R.id.show_password_cb);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mForgetTv.setOnClickListener(this);
        mLoginBt.setOnClickListener(this);
        mRegisterTv.setOnClickListener(this);
        mShowPasswordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        LoginActivity loginActivity = ((LoginActivity) getActivity());
        switch (view.getId()) {
            case R.id.forget_tv:
                loginActivity.replaceFragment(FragmentTag.FORGET_PASSWORD_FRAGMENT);
                break;
            case R.id.login_bt:
                login();
                break;
            case R.id.register_tv:
                loginActivity.replaceFragment(FragmentTag.REGISTER_FRAGMENT);
                break;
        }
    }

    private void login() {
        //login
        AppApplication.getInstance().isLogin = true;
        getActivity().startActivity(MainTabActivity.createIntent(context));
        getActivity().finish();
    }
}
