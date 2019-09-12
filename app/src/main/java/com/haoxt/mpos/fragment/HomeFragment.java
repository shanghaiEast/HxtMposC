package com.haoxt.mpos.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.adapter.SaleListAdapter;
import com.haoxt.mpos.common.Constants;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.ListSaleDetail;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;

public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private RecyclerView mSaleList;
    private ArrayList<ListSaleDetail> mList;
    private TextView mNoDataLy;
    private TextView mOrdinaryTv;
    private TextView mSuperTv;
    private TextView mQrTv;
    private TextView mQuickPassTv;

    //单例
    public static HomeFragment newInstance() {
        return HomeFragment.HomeFragmentFactory.homeFragment;
    }

    private static final class HomeFragmentFactory {
        public static final HomeFragment homeFragment = new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
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
        mOrdinaryTv = view.findViewById(R.id.ordinary_tv);
        mSuperTv = view.findViewById(R.id.super_tv);
        mQrTv = view.findViewById(R.id.qr_tv);
        mQuickPassTv = view.findViewById(R.id.quick_pass_tv);
        mSaleList = view.findViewById(R.id.sale_list);
        mNoDataLy = view.findViewById(R.id.no_data_ly);
        mSaleList.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void initData() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        for (int i = 0; i < 10; i++) {
            ListSaleDetail listSaleDetail = new ListSaleDetail();
            listSaleDetail.id = i;
            listSaleDetail.setSalemoney("支付宝尾号 (2234)交易 236.00元");
            listSaleDetail.setSaletime("交易日期：2019/12/13 15:23:11");
            if (i == 0) {
                listSaleDetail.setTypename("最新");
            } else {
                listSaleDetail.setTypename("今天");
            }
            mList.add(listSaleDetail);
        }
        if (mList == null && mList.size() < 1) {
            mNoDataLy.setVisibility(View.VISIBLE);
            mSaleList.setVisibility(View.INVISIBLE);
        } else {
            mNoDataLy.setVisibility(View.INVISIBLE);
            mSaleList.setVisibility(View.VISIBLE);
            mSaleList.setAdapter(new SaleListAdapter(context, mList));
        }
    }

    @Override
    public void initEvent() {
        mOrdinaryTv.setOnClickListener(this);
        mSuperTv.setOnClickListener(this);
        mQrTv.setOnClickListener(this);
        mQuickPassTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        int payType = -1;
        switch (view.getId()) {
            case R.id.ordinary_tv:
                payType = PayType.ORDINARY;
                break;
            case R.id.super_tv:
                payType = PayType.SUPER;
                break;
            case R.id.qr_tv:
                payType = PayType.WE_CHAT;
                break;
            case R.id.quick_pass_tv:
                payType = PayType.QUICK_PASS;
                break;
        }
        if (payType != -1) {
            bundle.putInt(Constants.PAY_TYPE, payType);
            getActivity().startActivity(PayActivity.createIntent(context, bundle));
        }
    }


}
