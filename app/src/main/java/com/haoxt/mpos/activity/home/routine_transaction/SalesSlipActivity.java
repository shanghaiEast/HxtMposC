package com.haoxt.mpos.activity.home.routine_transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.util.BitmapUtil;
import com.haoxt.mpos.util.DateUtil;
import com.haoxt.mpos.util.HttpRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

import static tft.mpos.library.util.CommonUtil.toActivity;

/**
 * 电子签购单
 */
public class SalesSlipActivity extends BaseActivity implements View.OnClickListener {

    private Button mConfirmBt;
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
        setContentView(R.layout.fragment_sales_slip);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    private TextView deviceSn,merchantName,merchantNumber,acquiringBank,termValidity,cardOrganization,
            issuingBank,cardNo,transationType,transationTime,searchNumber,trancastionAmount;
    private ImageView purchaseImg;

    @Override
    public void initView() {

        deviceSn =  findView(R.id.sales_slip_device_sn);
        merchantName =  findView(R.id.sales_slip_merchant_name);
        merchantNumber =   findView(R.id.sales_slip_merchant_number);
        acquiringBank =  findView(R.id.sales_slip_acquiring_bank);
        termValidity =   findView(R.id.sales_slip_term_validity);
        cardOrganization =   findView(R.id.sales_slip_card_organization);
        issuingBank =  findView(R.id.sales_slip_issuing_bank);
        cardNo =   findView(R.id.sales_slip_card_no);
        transationType = findView(R.id.sales_slip_transation_type);
        transationTime =  findView(R.id.sales_slip_transation_time);
        searchNumber =  findView(R.id.sales_slip_search_number);
        trancastionAmount =  findView(R.id.sales_slip_search_amount);
        purchaseImg = findView(R.id.salaspurchase_purchase_img);

        mConfirmBt = findView(R.id.confirm_bt);
    }

    @Override
    public void initData() {

        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");

        this.getSalesAlipData(pageData);

    }

    /**
     * 查询签购单
     * @param pageData
     */
    private void getSalesAlipData(HashMap<String, Object> pageData) {
        String cseqNo = pageData.get("systemsTraceAuditNumber").toString();
        String batchNo = pageData.get("batchNo").toString();
        String trmNo = pageData.get("cardAcceptorTerminalId").toString();
        String date = DateUtil.getDays();

        HttpRequest.qrySignBillDtl(cseqNo, batchNo,trmNo,date,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

                if("000000".equals(dataMap.get("rspCd").toString())){

                    deviceSn.setText(userData.get("CORG_TRM_NO")==null?"":userData.get("CORG_TRM_NO").toString());
                    merchantName.setText(userData.get("CORG_MERC_NM")==null?"":userData.get("CORG_MERC_NM").toString());
                    merchantNumber.setText(userData.get("CORG_MERC_ID")==null?"":userData.get("CORG_MERC_ID").toString());
                    acquiringBank.setText(userData.get("CRD_EXP_DT")==null?"":userData.get("CRD_EXP_DT").toString());
                    termValidity.setText(userData.get("ORGA")==null?"":userData.get("ORGA").toString());
                    cardOrganization.setText(userData.get("BANK_NAME")==null?"":userData.get("BANK_NAME").toString());
                    issuingBank.setText(userData.get("CRD_FLG")==null?"":userData.get("CRD_FLG").toString());
                    cardNo.setText(userData.get("CRD_NO")==null?"":userData.get("CRD_NO").toString());
                    transationType.setText(userData.get("TXN_CD")==null?"":userData.get("TXN_CD").toString());
                    transationTime.setText(userData.get("TXN_TM")==null?"":userData.get("TXN_TM").toString());
                    searchNumber.setText(userData.get("AUT_CD")==null?"":userData.get("AUT_CD").toString());
                    trancastionAmount.setText(userData.get("TXN_AMT")==null?"":userData.get("TXN_AMT").toString());

                    String purchaseImgtx = userData.get("FILE_NO")==null?"":userData.get("FILE_NO").toString();
                    purchaseImg.setImageBitmap(BitmapUtil.base64ToBitmap(purchaseImgtx));


                }else{
                    showShortToast("查询失败");
                }

            }
        });

    }

    @Override
    public void initEvent() {
        mConfirmBt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:

                Intent intent = new Intent(context, TransationResultsActivity.class);
                intent.putExtra("pageData",(Serializable)pageData);
                toActivity(intent);
                break;
        }
    }
}
