package com.haoxt.mpos.activity.home.scanqrcode_transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.home.routine_transaction.StartTransationActivity;
import com.zxing.activity.CaptureActivity;

import java.io.Serializable;
import java.util.HashMap;

import tft.mpos.library.interfaces.ActivityPresenter;

/**
 * 扫描二维码
 */
public class ScanQRcodeActivity extends CaptureActivity implements ActivityPresenter, View.OnClickListener {



    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ScanQRcodeActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_think_change);
        init(this, findViewById(R.id.svCameraScan), findViewById(R.id.vfvCameraScan));

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    private ImageView type;
    private TextView amount;
    @Override
    public void initView() {

        type = this.findViewById(R.id.scan_code_type);
        amount = this.findViewById(R.id.scan_code_amount);
//        findViewById(R.id.svCameraScan),
//        findViewById(R.id.vfvCameraScan);
    }

    private String payType;
    private HashMap<String, Object> pageData;
    @Override
    public void initData() {

        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");

    }

    @Override
    public void initEvent() {
        findViewById(R.id.ivCameraScanLight).setOnClickListener(this);
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCameraScanLight:


//                switchLight(! isOn());
                break;
            default:
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onReturnClick(View v) {

    }

    @Override
    public void onForwardClick(View v) {

//        CommonUtil.toActivity(QRCodeActivity.createIntent(context, 1));

    }
}
