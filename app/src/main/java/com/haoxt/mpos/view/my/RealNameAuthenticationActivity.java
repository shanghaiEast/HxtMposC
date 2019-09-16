package com.haoxt.mpos.view.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.haoxt.mpos.R;

import cn.cloudwalk.libproject.Contants;
import cn.cloudwalk.libproject.OcrCameraActivity;
import cn.cloudwalk.libproject.util.ImgUtil;
import tft.mpos.library.base.BaseActivity;

/**
 * 实名认证 Activity
 *
 * @author baowen
 * @use toActivity(SettingActivity.createIntent ( ...));
 */
public class RealNameAuthenticationActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "SettingActivity";
    private final static int /*REQ_PHOTO1 = 1,*/ REQ_CAMERA1 = 2, /*REQ_PHOTO2 = 3,*/
            REQ_CAMERA2 = 4;
    public static String licence = "MDYxNjE2bm9kZXZpY2Vjd2F1dGhvcml6ZfXn5efk5ubk/OXm4OXn5uP35ubn5ODm4JHl5ubr5ebmouvl5ubr5ebE5uvl5ubr5cjm5uvl5ubgq+bn6+fr5+vX5+Dn6+fr597r5+bm5uXm/uU=";
    private ImageView card_side, card_front;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, RealNameAuthenticationActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_name_authentication);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ImageView cardSide, cardFront;
    private EditText tv_name, tv_id, tx_valid;
    private RadioButton rb1, rb2;
//	private Button


    @Override
    public void initView() {//必须调用

        tv_name = findView(R.id.real_name_auth_idcard_name);
        tv_id = findView(R.id.real_name_auth_idcard_number);

        tx_valid = findView(R.id.real_name_auth_idcard_idcard_valid);
        rb1 = findView(R.id.real_name_auth_idcard_rdb1);
        rb2 = findView(R.id.real_name_auth_idcard_rdb2);
        card_side = findView(R.id.btn_real_name_auth_id_card_side);
        card_front = findView(R.id.btn_real_name_auth_id_card_front);

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {//必须调用
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

        findView(R.id.btn_real_name_auth_id_card_side).setOnClickListener(this);
        findView(R.id.btn_real_name_auth_id_card_front).setOnClickListener(this);
        findView(R.id.btn_real_name_authentication_upload).setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_real_name_auth_id_card_side:
//				showShortToast("onClick  ivSettingHead");
                GoToActivity(/*REQ_PHOTO1, */REQ_CAMERA1);
                break;
            case R.id.btn_real_name_auth_id_card_front:
                GoToActivity(/*REQ_PHOTO2, */REQ_CAMERA2);
//				toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.btn_real_name_authentication_upload:
                toActivity(MyBankCardAddActivity.createIntent(context));
                break;
            default:
                break;
        }

    }

    public void GoToActivity(final int req_camera) {
        Intent intent = new Intent(RealNameAuthenticationActivity.this, OcrCameraActivity.class);
        intent.putExtra("LICENCE", licence);
        if (req_camera == REQ_CAMERA2) {
            intent.putExtra(Contants.OCR_FLAG, Contants.OCR_FLAG_IDBACK);
        } else {
            intent.putExtra(Contants.OCR_FLAG, Contants.OCR_FLAG_IDFRONT);
        }
        startActivityForResult(intent, req_camera);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CAMERA1) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("filepath_key");
                card_side.setImageBitmap(ImgUtil.getBitmapByPath(path));
            }
        } else if (requestCode == REQ_CAMERA2) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("filepath_key");
                card_front.setImageBitmap(ImgUtil.getBitmapByPath(path));
            }
        }
    }

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
