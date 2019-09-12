package com.haoxt.mpos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.haoxt.mpos.R;
import com.haoxt.mpos.common.FragmentTag;
import com.haoxt.mpos.view.transaction.TransactionDetailFragment;

import tft.mpos.library.base.BaseActivity;

/**
 * 登录界面Activity
 *
 * @author baowen
 * @use toActivity(SettingActivity.createIntent ( ...));
 */
public class TransactionActivity extends BaseActivity {
    private static final String TAG = "TransactionActivity";

    private TransactionDetailFragment mTransactionDetailFragment;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TransactionActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mTransactionDetailFragment = TransactionDetailFragment.newInstance();
        if (mTransactionDetailFragment != null) {
            replaceFragment(FragmentTag.TRANSACTION_DETAIL_FRAGMENT);
        }
    }

    public void replaceFragment(String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case FragmentTag.TRANSACTION_DETAIL_FRAGMENT:
                fragment = mTransactionDetailFragment;
                break;
        }

        //addToBackStack:把fragment加入到栈结构.
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(fragmentTag).commitAllowingStateLoss();
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
