package com.haoxt.mpos.activity.home.routine_transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.haoxt.mpos.R;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.signture.SignaturePad;
import com.haoxt.mpos.util.BitmapUtil;
import com.haoxt.mpos.util.DateUtil;
import com.haoxt.mpos.util.HttpRequest;
import com.haoxt.mpos.widget.MyDialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

import static tft.mpos.library.util.CommonUtil.toActivity;

/**
 * 签购单
 */
public class SignaturePopup extends MyDialog implements View.OnClickListener{

    private LinearLayout mLayout;
    private SignaturePad mSignaturePad;
    private HashMap<String, Object> pageData;
    Context thisContext;
//    private MyClickListener signaturePopupClickListener;

//    public SignaturePopup(Context context,MyClickListener listener) {
//        super(context);
//        initView();
//        signaturePopupClickListener = listener;
//    }

    public SignaturePopup(Context context, HashMap msg) {
        super(context);
        initView();
        pageData = msg;
        thisContext = context;
    }

    private Button btn_close,btn_rewrite,btn_confirm;


    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mLayout = (LinearLayout) inflater.inflate(R.layout.popup_signature, null);

        btn_close = mLayout.findViewById(R.id.btn_close);
        btn_rewrite = mLayout.findViewById(R.id.btn_rewrite);
        btn_confirm = mLayout.findViewById(R.id.btn_confirm);

        btn_close.setOnClickListener(this);
        btn_rewrite.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

        mSignaturePad = mLayout.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onSigned() {
                btn_confirm.setEnabled(true);
                btn_confirm.setTextColor(getContext().getResources().getColor(R.color.color_508FF7));
            }

            @Override
            public void onClear() {
                btn_confirm.setEnabled(false);
                btn_confirm.setTextColor(getContext().getResources().getColor(R.color.gray_bbc0c8));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                if (isShowing())
                    cancel();
                break;

            case R.id.btn_rewrite:
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mSignaturePad.clear();
                    }
                };

                break;

            case R.id.btn_confirm:
                if (isShowing())
                    cancel();

                String data = BitmapUtil.Bitmap2String(mSignaturePad.getSignatureBitmap(), 30);
                pageData.put("strBaseImg",data);
                uploadSignature(pageData);


                break;
        }
    }

    private void uploadSignature(HashMap<String, Object> pageData) {

        String systemsTraceAuditNumber = pageData.get("systemsTraceAuditNumber").toString();
        String batchNo = pageData.get("batchNo").toString();
        String cardAcceptorTerminalId = pageData.get("cardAcceptorTerminalId").toString();
        String date = DateUtil.getDays();
        String img = pageData.get("strBaseImg").toString();

        HttpRequest.upLoadSign(systemsTraceAuditNumber, batchNo,cardAcceptorTerminalId,date,img,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

                if("000000".equals(dataMap.get("rspCd").toString())){

                    Intent intent = new Intent(getContext(), SalesSlipActivity.class);
                    intent.putExtra("pageData",(Serializable)pageData);
                    toActivity((Activity) thisContext, intent);

                }else{

                }

            }
        });

    }

    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
        this.setCancelable(false);
        this.setContentView(mLayout);
    }

    /**
     * 用于回调的抽象类
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }
}
