package com.haoxt.mpos.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.mpos.R;
import com.haoxt.mpos.util.HttpRequest;

import java.util.ArrayList;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.ui.PlacePickerWindow;
import tft.mpos.library.util.StringUtil;

/** 机具申领 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class MyPOSApplyActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";
	private static final int REQUEST_TO_PLACE_PICKER = 32;
	private String province,city;

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, MyPOSApplyActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pos_apply);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView area;
	private EditText name,phone,address;
	private Button commit;

	@Override
	public void initView() {//必须调用

		area = findViewById(R.id.mypos_apply_info_area);

		name = findViewById(R.id.mypos_apply_info_name);
		phone = findViewById(R.id.mypos_apply_info_phone);
		address = findViewById(R.id.mypos_apply_info_address);
		commit = findViewById(R.id.mypos_apply_info_commit);

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用

		this.getPosInfo();

	}

	private void getPosInfo() {
		HttpRequest.deviceApplyInfo(0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){

					name.setText(userData.get("CONTACT")==null?"":userData.get("CONTACT").toString());
					phone.setText(userData.get("PHONE")==null?"":userData.get("PHONE").toString());
					address.setText(userData.get("ADDRESS")==null?"":userData.get("ADDRESS").toString());

					String pro = userData.get("PROV")==null?"":userData.get("PROV").toString();
					String city1 = userData.get("CITY")==null?"":userData.get("CITY").toString();
					String area1 = userData.get("AREA")==null?"":userData.get("AREA").toString();

					area.setText(pro+city1+area1);
					commit.setEnabled(false);
					commit.setBackgroundResource(R.drawable.shape_btn_red_press);

				}else{
					showShortToast("获取失败");
				}
			}
		});
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.mypos_apply_info_commit).setOnClickListener(this);
		findViewById(R.id.mypos_apply_info_area).setOnClickListener(this);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.mypos_apply_info_commit:
				this.commitData();

				break;

			case R.id.mypos_apply_info_area:
				toActivity(PlacePickerWindow.createIntent(context, getPackageName(), 2), REQUEST_TO_PLACE_PICKER, false);
				break;
			default:
				break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode){
			case REQUEST_TO_PLACE_PICKER:
				if (data != null) {
					ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
					if (placeList != null) {
						String place = "";
						for (String s : placeList) {
							place += StringUtil.getTrimedString(s);
						}
						province = placeList.get(0);
						city = placeList.get(1);
						area.setText(place);
					}
				}
				break;

			default:
				break;
		}
	}

	private void commitData() {

		String nametx,phonetx,addresstx;

		nametx = name.getText().toString();
		phonetx = phone.getText().toString();
		addresstx = address.getText().toString();

		province = "";
		city = "";

		if (nametx.equals("")) {
			Toast.makeText(this, "请输入联系人", Toast.LENGTH_SHORT).show();
			return;
		}

		if (phonetx.equals("")) {
			Toast.makeText(this, "请输入联系方式", Toast.LENGTH_SHORT).show();
			return;
		}

		if (addresstx.equals("")) {
			Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
			return;
		}

		if (province.equals("")) {
			Toast.makeText(this, "请选择省市", Toast.LENGTH_SHORT).show();
			return;
		}


		HttpRequest.deviceApply(nametx, phonetx,province,city,"",addresstx,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

				if("000000".equals(dataMap.get("rspCd").toString())){
					showShortToast("成功");
				}else{
					showShortToast("失败");
				}
			}
		});
	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
