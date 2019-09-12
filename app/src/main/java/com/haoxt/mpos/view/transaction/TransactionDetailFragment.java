package com.haoxt.mpos.view.transaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.adapter.TransactionDetailAdapter;
import com.haoxt.mpos.entity.TransactionDetail;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;

public class TransactionDetailFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackIv;
    private RecyclerView mRecycler;
    public static String[] keys = {"交易卡号", "交易金额", "手续费", "交易时间", "订单号", "签购单"};
    public static String[] values = {"46456436*****6535335", "123.00", "0.01", "2019-04-18 12:12:34", "35334535223", "点击查看签购单"};
    private ArrayList<TransactionDetail> mList;


    //单例
    public static TransactionDetailFragment newInstance() {
        return TransactionDetailFragment.TransactionDetailFragmentFactory.transactionDetailFragment;
    }

    private static final class TransactionDetailFragmentFactory {
        public static final TransactionDetailFragment transactionDetailFragment = new TransactionDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_transaction_detail);
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
        mBackIv = view.findViewById(R.id.back_iv);
        mRecycler = view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        initList();
        mRecycler.setAdapter(new TransactionDetailAdapter(context, mList));
    }

    private void initList() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        for (int i = 0; i < keys.length; i++) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setValue(values[i]);
            transactionDetail.setKey(keys[i]);
            mList.add(transactionDetail);
        }
    }

    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                getActivity().onBackPressed();
                break;
        }
    }
}
