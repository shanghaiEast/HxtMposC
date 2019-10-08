package com.haoxt.mpos.activity.home.scanqrcode_transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.fragment.tab.MainTabActivity;
import com.haoxt.mpos.signture.SignaturePad;
import com.haoxt.mpos.activity.home.routine_transaction.SignaturePopup;
import com.haoxt.mpos.activity.home.routine_transaction.StartTransationActivity;
import com.haoxt.mpos.util.HttpRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/**
 * 交易等待确认
 */
public class ConfirmTransationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private Button mQueryBt;
    private SignaturePopup mSignaturePopup;
    private SignaturePad mSignaturePad;
    private TextView mSaveButton;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ConfirmTransationActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_swipe_result);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    @Override
    public void initView() {
        mBackIv = findView(R.id.back_iv);
        mQueryBt = findView(R.id.query_bt);


    }

    private String payType;
    private HashMap<String, Object> pageData;
    @Override
    public void initData() {
        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");

        this.gotoTransation(pageData);
    }

    /**
     * 上送交易
     * @param pageData
     */
    private void gotoTransation(HashMap<String, Object> pageData) {
        String snNo = AppApplication.getInstance().getSnNo();
        String mercId = AppApplication.getInstance().getMerchantId();
        String amount = pageData.get("acount").toString();
        String scanStr = pageData.get("scanStr").toString();
        String scantype = pageData.get("scantype").toString();
        String province =  pageData.get("province").toString();
        String city =  pageData.get("city").toString();
        String district =  pageData.get("district").toString();

        HttpRequest.transation(snNo, mercId,"0.01",scanStr,scantype,province,city,district,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
                Map<String, Object> data = (Map<String, Object>) userData.get("data");
                pageData.put("sourceTranDate",data.get("sourceTranDate")==null?"":data.get("sourceTranDate").toString());
                pageData.put("sourcePosRequestId",data.get("sourcePosRequestId")==null?"":data.get("sourcePosRequestId").toString());
                pageData.put("sourceBatchNo",data.get("sourceBatchNo")==null?"":data.get("sourceBatchNo").toString());
                pageData.put("systemsTraceAuditNumber",data.get("systemsTraceAuditNumber")==null?"":data.get("systemsTraceAuditNumber").toString());
                pageData.put("batchNo",data.get("batchNo")==null?"":data.get("batchNo").toString());
                pageData.put("cardAcceptorTerminalId",data.get("cardAcceptorTerminalId")==null?"":data.get("cardAcceptorTerminalId").toString());

                if("000000".equals(dataMap.get("rspCd").toString())&&data.get("responseCode").toString()=="00"){ //成功
                    showPop();
                }else if("000000".equals(dataMap.get("rspCd").toString())&&data.get("responseCode").toString()=="P0"){ //查询5次
                    getTransationResult(pageData);
                }else {
                    showShortToast("查询失败");
                }

            }
        });
    }

    /**
     * 查询交易
     * @param pageData
     */
    private void getTransationResult(HashMap<String, Object> pageData) {
        String snNo = AppApplication.getInstance().getSnNo();
        String mercId = AppApplication.getInstance().getMerchantId();
        String sourceTranDate = pageData.get("sourceTranDate").toString();
        String sourcePosRequestId = pageData.get("sourcePosRequestId").toString();
        String sourceBatchNo = pageData.get("sourceBatchNo").toString();


        HttpRequest.transationSearch(snNo, mercId,sourceTranDate,sourcePosRequestId,sourceBatchNo,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

                if("000000".equals(dataMap.get("rspCd").toString())){
                    showPop();

                }else{
                    showShortToast("查询失败");
                }

            }
        });
    }



    @Override
    public void initEvent() {
        mBackIv.setOnClickListener(this);
        mQueryBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.query_bt:
                showPop();
                break;
        }
    }

    private void showPop() {

        if (mSignaturePopup != null) {
            mSignaturePopup = null;
        }
        mSignaturePopup = new SignaturePopup(context,pageData);

//        mSignaturePad = findViewById(R.id.signature_pad);
//        mSaveButton =  findViewById(R.id.confirm_tv);
//
//        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
//            @Override
//            public void onSigned() {
//                mSaveButton.setEnabled(true);
//                mSaveButton.setTextColor(getResources().getColor(R.color.color_508FF7));
//            }
//
//            @Override
//            public void onClear() {
//                mSaveButton.setEnabled(false);
//                mSaveButton.setTextColor(getResources().getColor(R.color.gray_bbc0c8));
//            }
//        });

        mSignaturePopup.show();
    }
}
