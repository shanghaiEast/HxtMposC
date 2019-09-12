package com.haoxt.mpos.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.AboutActivity;

import tft.mpos.library.base.BaseActivity;

/** 绑定银行卡 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class MyBankCardAddActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, MyBankCardAddActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_bank_card_add);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private EditText real_name_auth_debit_card_name,real_name_auth_debit_card_account,real_name_auth_debit_card_openbank,real_name_auth_debicard_idcard_area,real_name_auth_debicard_subbranch;

	private ImageView btn_real_name_auth_debit_card_side ;

	@Override
	public void initView() {//必须调用

		real_name_auth_debit_card_name = (EditText)findViewById(R.id.real_name_auth_debit_card_name);
		real_name_auth_debit_card_account = (EditText)findViewById(R.id.real_name_auth_debit_card_account);
		real_name_auth_debit_card_openbank = (EditText)findViewById(R.id.real_name_auth_debit_card_openbank);
		real_name_auth_debicard_idcard_area = (EditText)findViewById(R.id.real_name_auth_debicard_idcard_area);
		real_name_auth_debicard_subbranch = (EditText)findViewById(R.id.real_name_auth_debicard_subbranch);

//		btn_real_name_auth_debit_card_side = (ImageView)findViewById(R.id.btn_real_name_auth_debit_card_side);
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

		findView(R.id.btn_real_name_auth_debit_card_side).setOnClickListener(this);
		findView(R.id.real_name_auth_debit_card_openbank).setOnClickListener(this);
		findView(R.id.real_name_auth_debicard_subbranch).setOnClickListener(this);
		findView(R.id.btn_add_bank_msg_upload).setOnClickListener(this);

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
				break;
			case R.id.btn_real_name_auth_id_card_front:
//				toActivity(SettingActivity.createIntent(context));
				break;
			case R.id.btn_real_name_authentication_upload:
				toActivity(AboutActivity.createIntent(context));
				break;
			case R.id.btn_add_bank_msg_upload:
				toActivity(MerchantInfoAuthActivity.createIntent(context));
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
