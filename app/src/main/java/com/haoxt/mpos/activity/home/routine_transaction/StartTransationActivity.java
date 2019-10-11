package com.haoxt.mpos.activity.home.routine_transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.home.DeviceBindActivity;
import com.haoxt.mpos.activity.home.scanqrcode_transaction.ConfirmTransationActivity;
import com.haoxt.mpos.activity.home.scanqrcode_transaction.ScanQRcodeActivity;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.util.HttpRequest;
import com.haoxt.mpos.widget.MyDialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 交易金额输入 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class StartTransationActivity extends BaseActivity implements OnClickListener ,CompoundButton.OnCheckedChangeListener
{
	private static final String TAG = "SettingActivity";
	public static final int REQUEST_TO_CAMERA_SCAN = 22;
	public LocationClient mLocationClient = null;
	private StartTransationActivity.MyLocationListener myListener = new MyLocationListener();
	private String latitudeStr,longitudeStr,province,city,district;

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
		setContentView(R.layout.transation_enteramount);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		setLocation();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private ImageView mBackIv;
	private TextView mTitleTv;
	private EditText mNumberEt;
	private EditText mAuthCodeEt;
	private TextView bankCardTv;
	private TextView mBankCardTv,transationTitle;
	private MyDialog mDialog;
	private RelativeLayout mAliPayRl;
	private RelativeLayout mWeChatRl;
	private Button mPayBt;

	private String[] stringsRemarks = {}; //wechat,alipay;

	private CheckBox[] checkBoxes =  new CheckBox[2];
	@Override
	public void initView() {//必须调用

		mTitleTv = findView(R.id.title_tv);
		mNumberEt = findView(R.id.number_et);
//		mAuthCodeEt = findView(R.id.auth_code_et);
		mBankCardTv = findView(R.id.bank_card_tv);

		mAliPayRl = findView(R.id.ali_pay_rl);
		mWeChatRl = findView(R.id.we_chat_rl);

		mPayBt = findView(R.id.pay_bt);
		transationTitle = findView(R.id.transation_type_tx);

		checkBoxes[0] = findView(R.id.we_chat_cb);
		checkBoxes[1] = findView(R.id.ali_pay_cb);
		checkBoxes[0].setOnCheckedChangeListener(this);
		checkBoxes[1].setOnCheckedChangeListener(this);


//		wechat = findView(R.id.we_chat_cb);
//		alipay = findView(R.id.ali_pay_cb);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


//	private void showPop() {
//		if (mDialog != null) {
//			mDialog = null;
//		}
//		mDialog = new MyDialog(getActivity());
//		LayoutInflater inflater = getLayoutInflater();
//		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.popup, null);
//
//		Button payment = layout.findViewById(R.id.card_payment_bt);
//		TextView cancel = layout.findViewById(R.id.cancel_tv);
//		TextView amount = layout.findViewById(R.id.payment_amount_tv);
//
//		String acount = mNumberEt.getText().toString();
//        amount.setText(acount);
//
//		cancel.setOnClickListener(this);
//		payment.setOnClickListener(this);
//		mDialog.show();
//		mDialog.setCancelable(false);
//		mDialog.setContentView(layout);
//	}

//	private void dismissPop() {
//		if (mDialog != null && mDialog.isShowing()) {
//			mDialog.cancel();
//			mDialog = null;
//		}
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case REQUEST_TO_CAMERA_SCAN:
				if (data != null) {
					String scanStr = data.getStringExtra(ScanQRcodeActivity.RESULT_QRCODE_STRING);
					pageData.put("scanStr",scanStr);

					Intent intent = new Intent(context, ConfirmTransationActivity.class);
					intent.putExtra("pageData",(Serializable)pageData);
					startActivity(intent);

				}
			break;

			default:
				break;
		}

	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String payType;
	private HashMap< String, Object> pageData  = new HashMap<String, Object>();
	@Override
	public void initData() {//必须调用

		Intent intent =getIntent();
		payType = intent.getStringExtra("paytype");
		pageData.put("payType",payType);

		switch (Integer.parseInt(payType)){
			case 1:
				transationTitle.setText("普通收款");
			break;

			case 2:
				transationTitle.setText("超级收款");
				break;

			case 3:
				transationTitle.setText("微信/支付宝");
				mAliPayRl.setVisibility(View.VISIBLE);
				mWeChatRl.setVisibility(View.VISIBLE);
				break;

			case 4:
				transationTitle.setText("闪付优惠");
				break;

			default:
				break;

		}

		HttpRequest.getUserInfo(0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                if(!StringUtil.isEmpty(resultJson)){
                    Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                    Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

                    if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){

						String bankName = userData.get("STL_BANK_NAME")==null?"":userData.get("STL_BANK_NAME").toString();
						String cardNum = userData.get("CER_NO")==null?"":userData.get("CER_NO").toString();
						String result = cardNum.substring(cardNum.length()-4);

						mBankCardTv.setText(bankName+"("+result+")");

                    }else{
                        showShortToast("获取商户信息失败");
                    }
                }
            }
        });

	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

//		mBackIv.setOnClickListener(this);
//		mBankCardTv.setOnClickListener(this);
		mPayBt.setOnClickListener(this);


	}


	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		if(b) {
			for (int i = 0; i < checkBoxes.length; i++) {
				if ( checkBoxes[i].getId() == compoundButton.getId()) {
					checkBoxes[i].setChecked(true);
				} else {
					checkBoxes[i].setChecked(false);
				}
			}
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onReturnClick(View v) {
		finish();
	}

	@Override
	public void onClick(View view) {
		HashMap<String, Object> msg  = new HashMap<String, Object>();
		msg.put("payType",payType);

		switch (view.getId()) {


			case R.id.pay_bt:

				String acount = mNumberEt.getText().toString();
				if ("".equals(acount)){
					Toast.makeText(this, "请输入收款金额", Toast.LENGTH_SHORT).show();
					return;
				}
				pageData.put("amount",acount);
				pageData.put("longitude",longitudeStr);
				pageData.put("latitude",latitudeStr);
				pageData.put("province",province);
				pageData.put("city",city);
				pageData.put("district",district);

				if(!payType.equals("3")){ //普通
					if (payType.equals("1")){
						pageData.put("transType","deposit");
						pageData.put("actPayType","1");

					}else if (payType.equals("4")){
						pageData.put("transType","purchase");
						pageData.put("actPayType","0");
					}

					Intent intent = new Intent(context, ConnectPosActivity.class);
					intent.putExtra("pageData",(Serializable)pageData);
					startActivity(intent);

				}else{ //扫码

					if(checkBoxes[0].isChecked()){
						pageData.put("scantype","aliPay"); //支付宝
					}else if(checkBoxes[1].isChecked()){
						pageData.put("scantype","wechatPay"); //微信
					}else{
						Toast.makeText(this, "请选择付款方式", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent1 = ScanQRcodeActivity.createIntent(context);
					intent1.putExtra("pageData",(Serializable)pageData);
					toActivity(intent1, REQUEST_TO_CAMERA_SCAN);
				}

				break;

//			case R.id.card_payment_bt:
//
//				String acount = mNumberEt.getText().toString();
//				if ("".equals(acount)){
//					Toast.makeText(this, "请输入收款金额", Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//				pageData.put("amount",acount);
//				pageData.put("scantype","aliPay"); //支付宝
//				pageData.put("longitude",longitudeStr);
//				pageData.put("latitude",latitudeStr);
//				pageData.put("province",province);
//				pageData.put("city",city);
//				pageData.put("district",district);
//
//				if(!payType.equals("3")){ //普通
//					if (payType.equals("1")){
//						pageData.put("transType","deposit");
//						pageData.put("actPayType","1");
//
//					}else if (payType.equals("4")){
//						pageData.put("transType","purchase");
//						pageData.put("actPayType","0");
//					}
//
//					Intent intent = new Intent(context, ConnectPosActivity.class);
//					intent.putExtra("pageData",(Serializable)pageData);
//					startActivity(intent);
//
//				}else{ //扫码
//					toActivity(ScanQRcodeActivity.createIntent(context), REQUEST_TO_CAMERA_SCAN);
//
//				}
//				break;

//			case R.id.cancel_tv:
//				dismissPop();
//				break;

		}

	}

	private void setLocation() {

		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，设置定位模式，默认高精度
		//LocationMode.Hight_Accuracy：高精度；
		//LocationMode. Battery_Saving：低功耗；
		//LocationMode. Device_Sensors：仅使用设备；

		option.setCoorType("bd09ll");
		//可选，设置返回经纬度坐标类型，默认GCJ02
		//GCJ02：国测局坐标；
		//BD09ll：百度经纬度坐标；
		//BD09：百度墨卡托坐标；
		//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

		option.setScanSpan(1000);
		//可选，设置发起定位请求的间隔，int类型，单位ms
		//如果设置为0，则代表单次定位，即仅定位一次，默认为0
		//如果设置非0，需设置1000ms以上才有效

		option.setOpenGps(true);
		//可选，设置是否使用gps，默认false
		//使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(true);
		//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
		//可选，定位SDK内部是一个service，并放到了独立进程。
		//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
		//可选，设置是否收集Crash信息，默认收集，即参数为false

		option.setWifiCacheTimeOut(5*60*1000);
		//可选，V7.2版本新增能力
		//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

		option.setEnableSimulateGps(false);
		//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

		option.setIsNeedAddress(true);
		//可选，是否需要地址信息，默认为不需要，即参数为false
		//如果开发者需要获得当前点的地址信息，此处必须为true

		mLocationClient.setLocOption(option);
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

		mLocationClient.start();

	}


	public class MyLocationListener extends BDAbstractLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			//此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
			//以下只列举部分获取经纬度相关（常用）的结果信息
			//更多结果信息获取说明，请参照类参考中BDLocation类中的说明

			double latitude = location.getLatitude();    //获取纬度信息
			latitudeStr = String.valueOf(latitude);
			double longitude = location.getLongitude();    //获取经度信息
			longitudeStr = String.valueOf(longitude);
			float radius = location.getRadius();    //获取定位精度，默认值为0.0f



			String coorType = location.getCoorType();
			//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

			int errorCode = location.getLocType();
			//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

			String addr = location.getAddrStr();    //获取详细地址信息
			String country = location.getCountry();    //获取国家
			province = location.getProvince();    //获取省份
			city = location.getCity();    //获取城市
			district = location.getDistrict();    //获取区县
			String street = location.getStreet();    //获取街道信息
		}
	}


}
