package com.haoxt.mpos.activity.home.routine_transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.PayResults;

import java.util.HashMap;

import tft.mpos.library.base.BaseActivity;

/**
 * 结果返回页面
 */
public class TransationResultsActivity extends BaseActivity implements View.OnClickListener {

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

    private HashMap<String, Object> pageData;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, StartTransationActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_collecting_result);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

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

        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");

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




        int payType = Integer.parseInt(pageData.get("payType").toString());
        switch (payType) {
            case 0:
            case 1:
            case 2:
                mCardPayLy.setVisibility(View.VISIBLE);
                mQrPayLy.setVisibility(View.GONE);
                break;

            case 3:
                mCardPayLy.setVisibility(View.GONE);
                mQrPayLy.setVisibility(View.VISIBLE);
                break;

            default:
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
