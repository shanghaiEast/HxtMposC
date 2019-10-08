package com.haoxt.mpos.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.fragment.tab.MainTabActivity;
import com.haoxt.mpos.util.HttpRequest;

import java.io.Serializable;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 我的即时额度 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class MyImmediateQuotaActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, MyImmediateQuotaActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_inmediate_quota);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView quotaAmount,dayCreaditcard,timeCreaditcard,dayIccard,timeIccard;
	private Button realname_info_togo_realname_auth ;
	@Override
	public void initView() {//必须调用

		quotaAmount = (TextView)findViewById(R.id.my_inmediate_quota_amount);

		dayCreaditcard = (TextView)findViewById(R.id.tv_immediate_quota_day_creaditcard);
		timeCreaditcard = (TextView)findViewById(R.id.tv_immediate_quota_time_creaditcard);
		dayIccard = (TextView)findViewById(R.id.tv_immediate_quota_day_iccard);
		timeIccard = (TextView)findViewById(R.id.tv_immediate_quota_time_iccard);


	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用

		this.getQuotaData();

	}

	private void getQuotaData() {
		HttpRequest.quota(0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					quotaAmount.setText(userData.get("dayBal")==null?"":userData.get("dayBal").toString());

					dayCreaditcard.setText(userData.get("magDay")==null?"":userData.get("magDay").toString());
					timeCreaditcard.setText(userData.get("magSingle")==null?"":userData.get("magSingle").toString());
					dayIccard.setText(userData.get("icDay")==null?"":userData.get("icDay").toString());
					timeIccard.setText(userData.get("icSingle")==null?"":userData.get("icSingle").toString());

				}else{
					showShortToast("查询失败");
				}

			}
		});
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

		switch (view.getId()) {
			case R.id.realname_info_togo_realname_auth:
				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
				intent.putExtra("activityfrom", "realnameinfo");
				toActivity(intent);
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
