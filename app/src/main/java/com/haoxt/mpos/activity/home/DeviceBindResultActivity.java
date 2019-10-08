package com.haoxt.mpos.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.home.routine_transaction.StartTransationActivity;
import com.haoxt.mpos.activity.my.MyPOSInfoActivity;
import com.haoxt.mpos.entity.PayResults;

import java.util.HashMap;

import tft.mpos.library.base.BaseActivity;

/**
 * 绑定结果返回页面
 */
public class DeviceBindResultActivity extends BaseActivity implements View.OnClickListener {

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
        return new Intent(context, DeviceBindResultActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bind_result);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

        findView(R.id.go_home).setOnClickListener(this);
        findView(R.id.to_mypos).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_home:
                toActivity(MyPOSInfoActivity.createIntent(context));

                break;
            case R.id.to_mypos:

                break;
        }
    }

}
