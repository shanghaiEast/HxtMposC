package com.haoxt.mpos.activity.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.my.ReviseLoginPwdActivity;
import com.haoxt.mpos.activity.my.UpdatePhoneActivity;
import com.haoxt.mpos.adapter.TransactionDetailAdapter;
import com.haoxt.mpos.common.PayType;
import com.haoxt.mpos.entity.ListTransaction;
import com.haoxt.mpos.entity.TransactionDetail;
import com.haoxt.mpos.activity.home.routine_transaction.StartTransationActivity;

import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<TransactionDetail> mList;


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
        setContentView(R.layout.fragment_transaction_detail);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    private TextView income,type,cardNo,amount,fee,time,orderNo,purchase;
    @Override
    public void initView() {
        income =  findViewById(R.id.tv_order_detail_income);
        type = findViewById(R.id.tv_order_detail_pay_type);
        cardNo = findViewById(R.id.tv_order_detail_card_no);
        amount = findViewById(R.id.tv_order_detail_amount);
        fee = findViewById(R.id.tv_order_detail_fee);
        time = findViewById(R.id.tv_order_detail_time);
        orderNo = findViewById(R.id.tv_order_detail_no);
        purchase = findViewById(R.id.tv_order_detail_purchase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ListTransaction transaction;
    @Override
    public void initData() {
        transaction = (ListTransaction) getIntent().getSerializableExtra("transaction");
        income.setText(transaction.getTXN_AMT());
        String typetx = transaction.getTXN_CD();
        switch (Integer.parseInt(typetx)) {
            case PayType.ALI_PAY:
                typetx = "支付宝";
                break;
            case PayType.WE_CHAT:
                typetx = "微信";
                break;
            case PayType.ORDINARY:
                typetx = "刷卡";
                break;
        }
        type.setText(typetx);
        cardNo.setText(transaction.getCRD_NO());
        amount.setText(transaction.getTXN_AMT());
        fee.setText(transaction.getMERC_FEE_AMT());
        time.setText(transaction.getTXN_TM());
        orderNo.setText(transaction.getLOG_NO());
        purchase.setText(transaction.getMERC_FEE_AMT());

    }


    @Override
    public void initEvent() {
        findViewById(R.id.tv_order_detail_purchase).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_detail_purchase://前往签购单
//                toActivity(ReviseLoginPwdActivity.createIntent(context));
                break;


            default:
                break;
        }
    }
}
