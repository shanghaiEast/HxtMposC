package com.haoxt.mpos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.haoxt.mpos.R;
import com.haoxt.mpos.common.Constants;
import com.haoxt.mpos.common.FragmentTag;
import com.haoxt.mpos.view.home.CollectingResultsFragment;
import com.haoxt.mpos.view.home.ConnectPosFragment;
import com.haoxt.mpos.view.home.EnteramountFragment;
import com.haoxt.mpos.view.home.SalesSlipFragment;
import com.haoxt.mpos.view.home.SwipeResultsFragment;
import com.haoxt.mpos.view.home.ThinkChangeFragment;

import tft.mpos.library.base.BaseActivity;

public class PayActivity extends BaseActivity {

    private int mPayType;
    private Fragment mCollectingResultsFragment;
    private Fragment mConnectPosFragment;
    private Fragment mEnteramountFragment;
    private Fragment mSalesSlipFragment;
    private Fragment mSwipeResultsFragment;
    private Fragment mThinkChangeFragment;

    public static Intent createIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mCollectingResultsFragment = CollectingResultsFragment.newInstance();
        mConnectPosFragment = ConnectPosFragment.newInstance();
        mEnteramountFragment = EnteramountFragment.newInstance();
        mSalesSlipFragment = SalesSlipFragment.newInstance();
        mSwipeResultsFragment = SwipeResultsFragment.newInstance();
        mThinkChangeFragment = ThinkChangeFragment.newInstance();

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mPayType = intent.getIntExtra(Constants.PAY_TYPE, -1);
        replaceFragment(FragmentTag.ENTERAMOUNT_FRAGMENT);
    }

    @Override
    public void initEvent() {

    }

    public int getPayType() {
        return mPayType;
    }

    public void replaceFragment(String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case FragmentTag.ENTERAMOUNT_FRAGMENT:
                fragment = mEnteramountFragment;
                break;
            case FragmentTag.CONNECT_POS_FRAGMENT:
                fragment = mConnectPosFragment;
                break;
            case FragmentTag.SWIPE_RESULTS_FRAGMENT:
                fragment = mSwipeResultsFragment;
                break;
            case FragmentTag.SALES_SLIP_FRAGMENT:
                fragment = mSalesSlipFragment;
                break;
            case FragmentTag.COLLECTING_RESULTS_FRAGMENT:
                fragment = mCollectingResultsFragment;
                break;
            case FragmentTag.THINK_CHANGE_FRAGMENT:
                fragment = mThinkChangeFragment;
                break;
        }

        //addToBackStack:把fragment加入到栈结构.
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commitAllowingStateLoss();
        }
    }
}
