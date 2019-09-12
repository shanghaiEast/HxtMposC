package com.haoxt.mpos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.TransactionActivity;
import com.haoxt.mpos.adapter.TransactionListAdapter;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.ListTransaction;
import com.haoxt.mpos.widget.MyDialog;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;

/**
 * Description: <TransactionProvider><br>
 * Author:      chucai.he<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TransactionListFragment extends BaseFragment implements View.OnClickListener, TransactionListAdapter.OnItemClickListener {

    private LinearLayout mTimeScreenLy;
    private TextView mConditionScreenTv;
    private RecyclerView mSaleList;
    private ArrayList<ListTransaction> mList;
    private TextView mNoDataTv;
    private MyDialog mConditionPopup;
    private MyDialog mTimePopup;

    //单例
    public static TransactionListFragment newInstance() {
        return TransactionListFragment.TransactionListFragmentFactory.transactionListFragment;
    }

    private static final class TransactionListFragmentFactory {
        public static final TransactionListFragment transactionListFragment = new TransactionListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_transaction_list);
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
        mTimeScreenLy = view.findViewById(R.id.time_screen_ly);
        mConditionScreenTv = view.findViewById(R.id.condition_screen_tv);
        mNoDataTv = view.findViewById(R.id.no_data_tv);
        mSaleList = view.findViewById(R.id.sale_list);
        mSaleList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        getListTransaction();
        if (mList == null || mList.size() < 1) {
            mSaleList.setVisibility(View.GONE);
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.GONE);
            mSaleList.setVisibility(View.VISIBLE);
            TransactionListAdapter adapter = new TransactionListAdapter(context, mList);
            mSaleList.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }
    }

    //假数据
    private void getListTransaction() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        for (int i = 0; i < 10; i++) {
            ListTransaction listTransaction = new ListTransaction();
            listTransaction.setPayMoney("467.00");
            listTransaction.setPayTime("交易日期：2019/12/13 15:23:11");
            if (i % 3 == 0) {
                listTransaction.setPayNum("支付宝(2234)");
                listTransaction.setPayType(PayType.ALI_PAY);
            } else if (i % 3 == 1) {
                listTransaction.setPayNum("微信(2234)");
                listTransaction.setPayType(PayType.WE_CHAT);
            } else if (i % 3 == 2) {
                listTransaction.setPayNum("刷卡(2234)");
                listTransaction.setPayType(PayType.ORDINARY);
            }
            if (i % 2 == 0) {
                listTransaction.setPayResult(0);
            } else {
                listTransaction.setPayResult(1);
            }
            mList.add(listTransaction);
        }
    }

    @Override
    public void initEvent() {
        mTimeScreenLy.setOnClickListener(this);
        mConditionScreenTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_screen_ly:
                showTimePopup();
                break;
            case R.id.condition_screen_tv:
                showConditionPopup();
                break;
        }
    }


    @Override
    public void onItemClick() {
        startActivity(TransactionActivity.createIntent(context));
    }

    private void showConditionPopup() {
        if (mConditionPopup == null)
            mConditionPopup = new MyDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.popup_condition, null);
        TextView cancel_tv = layout.findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionPopup.cancel();
            }
        });
        mConditionPopup.show();
        mConditionPopup.setCancelable(false);
        mConditionPopup.setContentView(layout);
    }

    private void showTimePopup() {
        if (mTimePopup == null)
            mTimePopup = new MyDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.popup_time, null);

        mTimePopup.show();
        mTimePopup.setCancelable(false);
        mTimePopup.setContentView(layout);
    }
}
