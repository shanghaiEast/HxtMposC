package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.FragmentTag;

import tft.mpos.library.base.BaseFragment;

public class SalesSlipFragment extends BaseFragment implements View.OnClickListener {

    private Button mConfirmBt;

    //单例
    public static SalesSlipFragment newInstance() {
        return SalesSlipFragment.SalesSlipFragmentFactory.salesSlipFragment;
    }

    private static final class SalesSlipFragmentFactory {
        public static final SalesSlipFragment salesSlipFragment = new SalesSlipFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_sales_slip);
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
        mConfirmBt = findView(R.id.confirm_bt);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mConfirmBt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                ((PayActivity) getActivity()).replaceFragment(FragmentTag.COLLECTING_RESULTS_FRAGMENT);
                break;
        }
    }
}
