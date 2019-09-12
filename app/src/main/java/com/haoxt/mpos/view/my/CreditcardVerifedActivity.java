package com.haoxt.mpos.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxt.mpos.R;

import tft.mpos.library.base.BaseActivity;

/** 实名认证信息 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class CreditcardVerifedActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, CreditcardVerifedActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditcard_verifed);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private EditText  etBankcardNo,etPhoneNo;
	private TextView tvName,tvIdCard,tvVerifyCodeTip;
	private Button btn_submit_certification,  btn_bankcard_no;

	@Override
	public void initView() {//必须调用

		tvName = (TextView) findViewById(R.id.tv_name);
		tvIdCard = (TextView) findViewById(R.id.tv_idCard);
		etBankcardNo = (EditText) findViewById(R.id.et_bankcard_no);
		etPhoneNo = (EditText) findViewById(R.id.et_phone_no);

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

		findViewById(R.id.btn_submit_certification).setOnClickListener(this);
		findViewById(R.id.btn_bankcard_no).setOnClickListener(this);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.btn_submit_certification:

				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
				intent.putExtra("activityfrom", "creditverifed");
				toActivity(intent);

//				showShortToast("onClick  ivSettingHead");
				break;

			case R.id.btn_bankcard_no:
//				showShortToast("onClick  ivSettingHead");
				break;

			default:
				break;
		}

	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
