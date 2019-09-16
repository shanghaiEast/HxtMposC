package com.haoxt.mpos.view.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haoxt.mpos.R;
import com.haoxt.mpos.adapter.DeviceAdapter;
import com.haoxt.mpos.model.DeviceEntity;
import com.whty.bluetooth.manage.util.BluetoothStruct;
import com.whty.comm.inter.ICommunication;
import com.whty.tymposapi.DeviceApi;
import com.whty.tymposapi.IDeviceDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;

/** 机具绑定 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class DeviceBindActivity extends BaseActivity implements AdapterView.OnItemClickListener {
	private static final String TAG = "SettingActivity";
	private DeviceEntity deviceInfo;
	private ListView lvDeviceList;
	private DeviceAdapter adapter;

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, DeviceBindActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_bind);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		initDevice();

		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initView() {//必须调用

		lvDeviceList = (ListView) findViewById(R.id.lv_device_list);


	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private DeviceApi deviceApi;
	private boolean isInited = false;
	private boolean deviceConnected = false;
	private BluetoothDevice currentDevice;
	private BroadcastReceiver receiver = null;
	public static ArrayList<BluetoothStruct> items = new ArrayList<>();
	public static ArrayList<DeviceEntity> devices = new ArrayList<>();
	private String mDeviceAddress;
	private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
	public  String flag="flag";

	private void initDevice(){

		deviceApi = new DeviceApi(context);
		deviceApi.setDelegate(deviceDelegate);

		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		intent.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		intent.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		intent.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
		intent.setPriority(-1000);

		context.registerReceiver(broadcastReceiver, intent);
		if (!isInited) {
			if (deviceApi.initDevice(ICommunication.BLUETOOTH_DEVICE)) {
				isInited = true;
			} else {
			}
		}
		if (!deviceConnected) {
			btAdapter.cancelDiscovery();
			btAdapter.startDiscovery();

			// BlueToothUtil.items.clear();
			items.clear();
			devices.clear();
		} else {
			Toast.makeText(context,
					"", Toast.LENGTH_SHORT)
					.show();
		}

		adapter = new DeviceAdapter(context);
		lvDeviceList.setAdapter(adapter);



	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();

			// 显示所有收到的消息及其细节
			for (int i = 0; i < lstName.length; i++) {
				String keyName = lstName[i].toString();
				Log.d(keyName, String.valueOf(b.get(keyName)));
			}

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {

				BluetoothDevice bluetoothDevice = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				int index = findBluetoothDevice(bluetoothDevice.getAddress(), items);
				if (index < 0 && bluetoothDevice.getName() != null) {
					items.add(new BluetoothStruct(bluetoothDevice.getName(),
							bluetoothDevice.getAddress(), bluetoothDevice));
					DeviceEntity deviceEntity = new DeviceEntity();

					if(bluetoothDevice.getName().startsWith("MP")){
						deviceEntity.setAdress(bluetoothDevice.getAddress());
						deviceEntity.setName(bluetoothDevice.getName());

						adapter.addDevice(deviceEntity);

//						devices.add(deviceEntity);

					}



//					Gson gson = new Gson();
//					String data = gson.toJson(devices);
//					Log.e("www", "设备信息---" + data);
//					//获取到蓝牙list发送事件
//					WritableMap map = Arguments.createMap();
//					map.putString("device", data);
//					map.putString("name", "设备信息");
//					sendEvent(mReactContext, "deviceInfo", map);
				}

			}


		}
	};

	private int findBluetoothDevice(String mac,
									ArrayList<BluetoothStruct> deviceList) {
		for (int i = 0; i < deviceList.size(); i++) {
			if (((BluetoothStruct) deviceList.get(i)).getMac().equals(mac))
				return i;
		}
		return -1;
	}


	IDeviceDelegate deviceDelegate = new IDeviceDelegate() {
		@Override
		public void onConnectedDevice(boolean b) {
			if (b) {
				Log.d("www", "onConnectedDevice-----" + b);
//				WritableMap map = Arguments.createMap();
//				map.putString("name", "连接成功");
//				sendEvent(mReactContext, "deviceInfo", map);
			}else {
//				WritableMap map = Arguments.createMap();
//				map.putString("name", "连接失败");
//				sendEvent(mReactContext, "deviceInfo", map);
			}

		}

		@Override
		public void onDisconnectedDevice(boolean b) {
			if (b) {
				Log.e("www", "onDisconnectedDevice--" + b);
//				WritableMap map = Arguments.createMap();
//				map.putString("name", "断开连接");
//				sendEvent(mReactContext, "deviceInfo", map);
			}

		}

		@Override
		public void onUpdateWorkingKey(boolean[] booleans) {

			Log.e("www", "onUpdateWorkingKey---" + booleans.length);
			if(booleans[0] && booleans[1]&& booleans[2]){
//				WritableMap map = Arguments.createMap();
//				map.putString("name", "工作密钥");
//				map.putString("isSuccess", "签到成功");
//
//				sendEvent(mReactContext, "deviceInfo", map);
			}

		}

		@Override
		public void onGetMacWithMKIndex(byte[] bytes) {

		}

		@Override
		public void onReadCard(HashMap hashMap) {

		}

		@Override
		public void onWaitingcard() {

		}

		@Override
		public void onReadCardData(HashMap hashMap) {
			Log.e("www", "onReadCardData---" + hashMap);
			if (hashMap != null && hashMap.get("errorCode").equals("9000")) {
				Map<String, String> newMap = new HashMap<>();
				newMap.put("pan", (String) hashMap.get("cardNumber"));
				String expiryDate = (String) hashMap.get("expiryDate");
				String date = expiryDate.substring(expiryDate.length() - 4, expiryDate.length());
				newMap.put("dateExpiration", date);
				newMap.put("ICSystemRelated", (String) hashMap.get("icData"));
				newMap.put("cardSequenceNumber", (String) hashMap.get("cardSeqNum"));
				newMap.put("track2", (String) hashMap.get("encTrack2Ex"));
//                newMap.put("pin", (String) hashMap.get("pin"));
				String cardType = (String) hashMap.get("cardType");
				if (cardType.equals("00")) {
					newMap.put("posEntryModeCode", "1");
				} else if (cardType.equals("01")) {
					newMap.put("posEntryModeCode", "2");
				} else if (cardType.equals("02")) {
					newMap.put("posEntryModeCode", "3");
				} else {
					newMap.put("posEntryModeCode", "");
				}


//				String cardInfo = new Gson().toJson(newMap);

//				WritableMap map = Arguments.createMap();
//				map.putString("name", "卡片信息");
//				map.putString("cardInfo", cardInfo);
//
//				sendEvent(mReactContext, "deviceInfo", map);
			}else if(hashMap != null && hashMap.get("errorCode").equals("8005")){
//				WritableMap map = Arguments.createMap();
//				map.putString("name", "取消交易");
//
//				sendEvent(mReactContext, "deviceInfo", map);
			}
		}



		@Override
		public void onGetPinBlock(HashMap hashMap) {
			Log.e("www1", "onGetPinBlock-----" + hashMap);
			if (hashMap != null && hashMap.get("errorCode").equals("9000")) {
			}
			String pin = (String) hashMap.get("pin");

//			WritableMap map = Arguments.createMap();
//			map.putString("name", "加密Pin");
//			map.putString("PinInfo", pin);
//
//			sendEvent(mReactContext, "deviceInfo", map);
		}

		@Override
		public void onDownGradeTransaction(HashMap hashMap) {

		}

		@Override
		public void onHandleError(HashMap hashMap) {

		}
	};


//	/**
//	 * 实现类，响应按钮点击事件
//	 */
//	DeviceAdapter.MyClickListener mListener = new DeviceAdapter.MyClickListener() {
//		@Override
//		public void myOnClick(int position, View v) {
//			deviceInfo = adapter.getDeviceInfo(position);
//			String mac = deviceInfo.getAdress();
//			String deviceName = deviceInfo.getName();
//
//			Log.d("deviceInfo", "deviceInfo-----" + mac+"================="+deviceName);
//
//		}
//	};



	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

//		findViewById(R.id.realname_info_togo_realname_auth).setOnClickListener(this);
		lvDeviceList.setOnItemClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}



	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


		deviceInfo = adapter.getDeviceInfo(i);

		Log.d("www", "onItemClick--" + i);


	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
