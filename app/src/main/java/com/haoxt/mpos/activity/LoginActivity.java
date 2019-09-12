package com.haoxt.mpos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.haoxt.mpos.R;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.common.FragmentTag;
import com.haoxt.mpos.fragment.ForgetPasswordFragment;
import com.haoxt.mpos.fragment.LoginFragment;
import com.haoxt.mpos.fragment.RegisterFragment;

import tft.mpos.library.base.BaseActivity;

/**
 * 登录界面Activity
 *
 * @author baowen
 * @use toActivity(SettingActivity.createIntent ( ...));
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private LoginFragment mLoginFragment;
    private ForgetPasswordFragment mForgetPasswordFragment;
    private RegisterFragment mRegisterFragment;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppApplication.getInstance().isLogin) {
            this.startActivity(MainTabActivity.createIntent(this));
            this.finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    @Override
    public void initView() {//必须调用
        mLoginFragment = LoginFragment.newInstance();
        mForgetPasswordFragment = ForgetPasswordFragment.newInstance();
        mRegisterFragment = RegisterFragment.newInstance();
        if (mLoginFragment != null) {
            replaceFragment(FragmentTag.LOGIN_FRAGMENT);
        }
    }

    public void replaceFragment(String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case FragmentTag.LOGIN_FRAGMENT:
                fragment = mLoginFragment;
                break;
            case FragmentTag.REGISTER_FRAGMENT:
                fragment = mRegisterFragment;
                break;
            case FragmentTag.FORGET_PASSWORD_FRAGMENT:
                fragment = mForgetPasswordFragment;
                break;
        }

        //addToBackStack:把fragment加入到栈结构.
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(null).commitAllowingStateLoss();
        }
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {//必须调用


    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
