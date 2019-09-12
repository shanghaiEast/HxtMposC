package com.haoxt.mpos.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haoxt.mpos.R;

import tft.mpos.library.base.BaseActivity;

/** 商户信息 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class MyUserInfoActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, MyUserInfoActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_user_info);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView merchantName,merchantAddress,merchantNo,merchantContacts,merchantNumber,merchantOpenTime,merchantStatus,merchantDebitRate,merchantCreditRate,merchantQrcodeRate;
	@Override
	public void initView() {//必须调用

		merchantName = (TextView)findViewById(R.id.tx_my_merchant_name);
		merchantAddress = (TextView)findViewById(R.id.tv_my_merchant_address);

		merchantNo = (TextView)findViewById(R.id.tv_myuser_merchant_no);
		merchantContacts = (TextView)findViewById(R.id.tv_myuser_merchant_contacts);
		merchantNumber = (TextView)findViewById(R.id.tv_myuser_merchant_number);
		merchantOpenTime = (TextView)findViewById(R.id.tv_myuser_merchant_opentime);
		merchantStatus = (TextView)findViewById(R.id.tv_myuser_merchant_status);
		merchantDebitRate = (TextView)findViewById(R.id.tv_myuser_merchant_debit_rate);
		merchantCreditRate = (TextView)findViewById(R.id.tv_myuser_merchant_credit_rate);
		merchantQrcodeRate = (TextView)findViewById(R.id.tv_myuser_merchant_qrcode_rate);


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

//		findViewById(R.id.realname_info_togo_realname_auth).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

//		switch (view.getId()) {
//			case R.id.realname_info_togo_realname_auth:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
//				break;
//
//			default:
//				break;
//		}

	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
