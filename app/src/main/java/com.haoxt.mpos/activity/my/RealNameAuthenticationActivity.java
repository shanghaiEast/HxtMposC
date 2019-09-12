package com.haoxt.mpos.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.AboutActivity;
import com.haoxt.mpos.activity.SettingActivity;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnBottomDragListener;
import tft.mpos.library.util.Log;
import tft.mpos.library.util.SettingUtil;

/** 实名认证 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class RealNameAuthenticationActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
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

	private ImageView cardSide,cardFront ;
	private EditText tv_name,tv_id,tx_valid;
	private RadioButton rb1,rb2;
//	private Button


	@Override
	public void initView() {//必须调用

		tv_name = findView(R.id.real_name_auth_idcard_name);
		tv_id = findView(R.id.real_name_auth_idcard_number);

		tx_valid = findView(R.id.real_name_auth_idcard_idcard_valid);
		rb1 = findView(R.id.real_name_auth_idcard_rdb1);
		rb2 = findView(R.id.real_name_auth_idcard_rdb2);
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
				break;
			case R.id.btn_real_name_auth_id_card_front:
//				toActivity(SettingActivity.createIntent(context));
				break;
			case R.id.btn_real_name_authentication_upload:
				toActivity(MyBankCardAddActivity.createIntent(context));
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
