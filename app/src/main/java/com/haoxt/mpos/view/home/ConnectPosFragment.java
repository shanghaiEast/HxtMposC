package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.FragmentTag;

import tft.mpos.library.base.BaseFragment;

public class ConnectPosFragment extends BaseFragment implements View.OnClickListener {

    private SignaturePopup mDialog;
    private ImageView mBackIv;
    private ImageView mConnectedIv;
    private LinearLayout mPosItemLy;

    //单例
    public static ConnectPosFragment newInstance() {
        return ConnectPosFragment.ConnectPosFragmentFactory.connectPosFragment;
    }

    private static final class ConnectPosFragmentFactory {
        public static final ConnectPosFragment connectPosFragment = new ConnectPosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_connect_pos);
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
        mBackIv = findView(R.id.back_iv);
        mConnectedIv = findView(R.id.connected_iv);
        mPosItemLy = findView(R.id.pos_item_ly);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
        mConnectedIv.setOnClickListener(this);
        mPosItemLy.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        PayActivity activity = (PayActivity) getActivity();
        switch (view.getId()) {
            case R.id.back_iv:
                activity.onBackPressed();
                break;
            case R.id.connected_iv:
                showPop();
                break;
            case R.id.pos_item_ly:
                activity.replaceFragment(FragmentTag.SWIPE_RESULTS_FRAGMENT);
                break;

        }

    }

    private void showPop() {
        if (mDialog != null)
            mDialog = null;
        mDialog = new SignaturePopup(((PayActivity) getActivity()));
        mDialog.show();
    }
}
