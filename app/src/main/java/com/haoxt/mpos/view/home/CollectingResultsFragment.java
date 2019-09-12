package com.haoxt.mpos.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.PayActivity;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.PayResults;

import tft.mpos.library.base.BaseFragment;

public class CollectingResultsFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mCardPayLy;
    private LinearLayout mQrPayLy;
    private ImageView mPayResultIv;
    private ImageView mQrPayResultIv;
    private TextView mPayResultTv;
    private TextView mUnknownTv;
    private TextView mPayMoneyTv;
    private TextView mQrPayResultTv;
    private TextView mQrPayMoneyTv;
    private TextView mQrUnknownTv;
    private Button mQueryBt;
    private Button mCancelBt;
    private PayResults mPayDetail;

    //单例
    public static CollectingResultsFragment newInstance() {
        return CollectingResultsFragmentFactory.collectingResultsFragment;
    }

    private static final class CollectingResultsFragmentFactory {
        public static final CollectingResultsFragment collectingResultsFragment = new CollectingResultsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_collecting_result);
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
        mCardPayLy = findView(R.id.card_pay_ly);
        mQrPayLy = findView(R.id.qr_pay_ly);
        mPayResultIv = findView(R.id.pay_result_iv);
        mQrPayResultIv = findView(R.id.qr_pay_result_iv);
        mPayResultTv = findView(R.id.pay_result_tv);
        mPayMoneyTv = findView(R.id.pay_money_tv);
        mUnknownTv = findView(R.id.unknown_tv);
        mQrPayResultTv = findView(R.id.qr_pay_result_tv);
        mQrPayMoneyTv = findView(R.id.qr_pay_money_tv);
        mQrUnknownTv = findView(R.id.qr_unknown_tv);
        mQueryBt = findView(R.id.query_bt);
        mCancelBt = findView(R.id.cancel_bt);

    }

    @Override
    public void initData() {
        setPayDetail();
        setPayLayout();
    }

    @Override
    public void initEvent() {
        mPayResultIv.setOnClickListener(this);
        mQrPayResultIv.setOnClickListener(this);
        mCancelBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_result_iv:
            case R.id.qr_pay_result_iv:
                setPayDetail();
                break;
            case R.id.cancel_bt:
                getActivity().onBackPressed();
                break;
        }
    }

    public void setPayLayout() {
        int payType = ((PayActivity) getActivity()).getPayType();
        switch (payType) {
            case PayType.QUICK_PASS:
            case PayType.ORDINARY:
            case PayType.SUPER:
                mCardPayLy.setVisibility(View.VISIBLE);
                mQrPayLy.setVisibility(View.GONE);
                break;
            case PayType.ALI_PAY:
            case PayType.WE_CHAT:
                mCardPayLy.setVisibility(View.GONE);
                mQrPayLy.setVisibility(View.VISIBLE);
                break;
        }

    }

    //    假数据
    public void setPayDetail() {
        if (mPayDetail == null) {
            PayResults payResults = new PayResults();
            payResults.setPayType(0);
            payResults.setPayResult(1);
            mPayDetail = payResults;
        }
        switch (mPayDetail.payResult) {
            case 0:
                mPayDetail.setPayType(1);
                mPayDetail.setPayMoney(239.00);
                mPayDetail.setPayResult(1);
                break;
            case 1:
                mPayDetail.setPayType(2);
                mPayDetail.setPayResult(2);
                break;
            case 2:
                mPayDetail.setPayType(3);
                mPayDetail.setPayResult(0);
                break;
        }
    }
}
