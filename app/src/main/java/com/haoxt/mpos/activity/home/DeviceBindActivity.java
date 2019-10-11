package com.haoxt.mpos.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.haoxt.mpos.R;
import com.haoxt.mpos.adapter.DeviceAdapter;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.model.DeviceEntity;
import com.haoxt.mpos.util.DeviceSharedMSG;
import com.haoxt.mpos.util.HttpRequest;
import com.haoxt.mpos.util.TYDeviceDelegate;
import com.haoxt.mpos.view.DialogThridUtils;
import com.whty.bluetooth.manage.util.BluetoothStruct;
import com.whty.bluetoothsdk.util.Utils;
import com.whty.comm.inter.ICommunication;
import com.whty.tymposapi.DeviceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 机具绑定 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class DeviceBindActivity extends BaseActivity implements AdapterView.OnItemClickListener {
	private static final String TAG = "SettingActivity";
	private DeviceEntity deviceInfo;
	private ListView lvDeviceList;
	private GifImageView gifImageView;
	GifDrawable gifDrawable;
	private DeviceAdapter adapter;
	private Dialog mDialog;
	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	private String latitudeStr,longitudeStr,deviceSn,bluetoothName,bluetoothAddress,type,switchingData;

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

		setLocation();

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		initDevice();

		//功能归类分区方法，必须调用>>>>>>>>>>

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


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@SuppressLint("WrongViewCast")
	@Override
	public void initView() {//必须调用

		lvDeviceList = (ListView) findViewById(R.id.lv_device_list);
		gifImageView = (GifImageView)findViewById(R.id.bindpos_gif);

		gifDrawable = (GifDrawable) gifImageView.getDrawable();
		gifDrawable.start();

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
	private DeviceHandler deviceHandler;
	private TYDeviceDelegate deviceDelegate;
	public  String flag="flag";

	private void initDevice(){

		deviceHandler = new DeviceHandler();
		deviceDelegate = new TYDeviceDelegate(deviceHandler);
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
					}
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

	/**
	 * 设备连接
	 *
	 * @param adress
	 */
	public void connectDevice(final String adress) {
		if(!adress.equals("")){
			new Thread(new Runnable() {
				@Override
				public void run() {
					deviceApi.connectDevice(adress);
				}
			}).start();
		}else{
			Log.d("设备连接--------->", "adress 为空");
		}
	}


    /**
     * 获取设备信息
     */
    public void getDeviceInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap deviceInfo = deviceApi.getDeviceIdentifyInfo();
                Log.e("Www", "getDeviceInfo----" + deviceInfo);
                if (deviceInfo != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ksn", (String) deviceInfo.get("ksn"));
                    map.put("factor", (String) deviceInfo.get("factor"));
                    map.put("cipher", (String) deviceInfo.get("cipher"));
                    String deviceInfoObj = new Gson().toJson(map);

                    deviceHandler.obtainMessage(
                            DeviceSharedMSG.GETDEVICEINFO, deviceInfoObj)
                            .sendToTarget();
                }
            }
        }).start();
    }

    /**
     * 获取终端类型
     */

    public void getKsn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap deviceKSNInfo = deviceApi.getDeviceKSNInfo();
                type = deviceKSNInfo.get("deviceType").toString();
                Log.e("www", "getKsn----" + deviceKSNInfo);
                if (deviceKSNInfo != null) {

//					Map<String, String> map = new HashMap<>();
//					map.put("type", (String) deviceKSNInfo.get("deviceType"));
//					String deviceInfoObj = new Gson().toJson(map);


					deviceHandler.obtainMessage(
							DeviceSharedMSG.GETDEVICEINFO)
							.sendToTarget();

                }
            }
        }).start();
    }

	/**
	 * 单独获取设备sn
	 */
	public void getDeviceSN() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				deviceSn = deviceApi.getDeviceSN();
				deviceHandler.obtainMessage(
						DeviceSharedMSG.GETDEVICESN)
						.sendToTarget();

			}
		}).start();

	}


	/**
     * ting断开设备
     */
    public void stopDevice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                deviceApi.disconnectDevice();
            }
        }).start();

    }

    /**
     * 开始刷卡
     *
     * @param money
     * @param time
     * @param a
     * @param b
     * @param c
     */
    public void startSwiper(final String money, String time, String a, String b, String c) {
        byte xiaofei = 0;
        byte bb = 0;
        byte cc = 0;
        if (a.equals("0x00")) {
            //代表消费
            xiaofei = 0x00;
        }
        if (b.equals("0x64")) {
            bb = 0x64;
        }
        if (c.equals("0x07")) {
            cc = 0x07;
        }
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.getDefault());
        final String terminalTime = format.format(new Date());
        final byte finalXiaofei = xiaofei;
        final byte finalBb = bb;
        final byte finalCc = cc;
        new Thread(new Runnable() {
            @Override
            public void run() {
                deviceApi.onlyReadCard(money, terminalTime.substring(2),
                        finalXiaofei, finalBb, finalCc);

            }
        }).start();

    }

    /**
     * 获取59域信息
     */
    public void getField59() {
        deviceApi = new DeviceApi(context);
        deviceApi.setDelegate(deviceDelegate);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String Feild59 = deviceApi.getIso8583Feild59();
                deviceHandler.obtainMessage(
                        DeviceSharedMSG.GETFIELD59, Feild59)
                        .sendToTarget();

            }
        }).start();

    }

	/**
	 * 更新主密钥
	 * @param mainkey
	 */
	public void updateMainKey(final String mainkey) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				deviceApi.updateMainKey(Utils
						.hexString2Bytes(mainkey));
			}
		}).start();
	}




	/**
	 * 获取pin
	 */
	public void getPin(){
		new Thread(new Runnable() {
			@Override
			public void run() {
//				HashMap encPinblock = deviceApi.getEncPinblock(null, (byte) 0, null);
			}
		}).start();
	}



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
        bluetoothName = deviceInfo.getName();
        bluetoothAddress = deviceInfo.getAdress();

		this.connectDevice(deviceInfo.getAdress());

		gifDrawable.stop();
		mDialog = DialogThridUtils.showWaitDialog(this, "正在绑定中请稍后...", false, true);

//		if (deviceConnected) {
//			new Thread() {
//				public void run() {
//					String tdk = "FA727B2F08101273A17712674D8CF21CAB3F69CE";
//					String pik = "4F0C5B17E48E20D69A2A284394F729DF46E3EF6D";
//					String mak = "4baa0a2ce07ba7f63ce73298";
//					deviceApi.updateWorkingKey(tdk, pik, mak);
//					deviceApi.updateMainKey(Utils
//							.hexString2Bytes("AE81CB2641C8F50E09EC69AAA8A2E1FA69C5EC72"));
//				}
//			}.start();
//		} else {
//		}

		Log.d("www", "onItemClick--" + i);

	}


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


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
			String province = location.getProvince();    //获取省份
			String city = location.getCity();    //获取城市
			String district = location.getDistrict();    //获取区县
			String street = location.getStreet();    //获取街道信息
		}
	}

			@SuppressLint("HandlerLeak")
	class DeviceHandler extends Handler {

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			String show_msg = "";
			switch (msg.what) {

				case DeviceSharedMSG.CONNECTEDDEVICE_SUCCESS:   //连接成功
					show_msg = (String) msg.obj;
                    Log.d("测试流程--------->", show_msg);
					getDeviceSN();

					break;

				case DeviceSharedMSG.CONNECTEDDEVICE_FAIL:		//连接失败
					show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
					break;

				case DeviceSharedMSG.DISCONNECTEDDEVICE_SUCCESS://断开成功
					show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
					break;

				case DeviceSharedMSG.DISCONNECTEDDEVICE_FAIL:	//断开失败
					show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
					break;

				case DeviceSharedMSG.UPDATEWORKINGKEY_SUCCESS:	//更新工作密钥成功

//                    uploadDeviceinfo();
					bindingDevice();
					break;

				case DeviceSharedMSG.UPDATEWORKINGKEY_FAIL:		//更新工作密钥失败
					show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
					break;

				case DeviceSharedMSG.DOWNGRADETRANSACTION:		//DOWNGRADETRANSACTION
					show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
					break;

				case DeviceSharedMSG.GETDEVICEINFO:            //获取设备信息成功

//					bindingDevice();
					uploadMainKey();
					break;


                case DeviceSharedMSG.GETDEVICESN:               //获取SN
					getKsn();

                    break;

				case DeviceSharedMSG.GETFIELD59:               //获取57
					show_msg = (String) msg.obj;
					Log.d("测试流程完成---------->", show_msg);
					break;

				case DeviceSharedMSG.UPDATEAPPTRANS:               //下发主密钥
					uploadMainKey();
					break;

                case DeviceSharedMSG.DEVICESIGN:               //去签到
                    signDevice();
                    break;

				case DeviceSharedMSG.DEVICESIGNDATA:           //签到成功，灌装密钥
					updateWorkingKey();
					break;

				case DeviceSharedMSG.DEVICESIGNSUCCESS:       //上传设备信息
					uploadDeviceinfo();
					break;

				case DeviceSharedMSG.UPLOADDEVICEINFOSUCCESS:		  //流程结束
					DialogThridUtils.closeDialog(mDialog);
					toActivity(DeviceBindResultActivity.createIntent(context));
					break;


                case DeviceSharedMSG.SHOW_MSG:
                    show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
                    break;


                default:
					break;
			}
		}

	}

    /**
     * 上传设备信息
     */
	public  void uploadDeviceinfo(){

	    String msg = bluetoothName+","+bluetoothAddress;

        HttpRequest.uploadBuletoothInfo(msg,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
                Map<String, Object>  data = (Map<String, Object>) userData.get("data");

                if("000000".equals(dataMap.get("rspCd").toString())){

					deviceHandler.obtainMessage(
							DeviceSharedMSG.UPLOADDEVICEINFOSUCCESS)
							.sendToTarget();




                }else{
                    showShortToast("查询失败");
                }
            }
        });


    }


	/**
	 * 更新工作密钥
	 */
	public void updateWorkingKey() {

//		String data = info.get("data").toString();
		String c1 = switchingData.substring(0,40);
		String c2 = switchingData.substring(40,80);
		String c3 = switchingData.substring(80,120);

		new Thread(new Runnable() {
			@Override
			public void run() {
				deviceApi.updateWorkingKey(c3, c1, c2);
			}
		}).start();
	}


	/**
     * 下发主密钥
//     * @param info
     */
	public void uploadMainKey() {
//		String sn = info.get("ksn").toString();
//		String type = info.get("type").toString();
		String mercid = AppApplication.getInstance().getMerchantId();

		HttpRequest.updateApptrans(deviceSn, type,mercid,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
				Map<String, Object>  data = (Map<String, Object>) userData.get("data");

				if("000000".equals(dataMap.get("rspCd").toString())&&"00".equals(data.get("msgTypeCode").toString())){

//					Map<String, String> map = new HashMap<>();
//					map.put("ksn", deviceSn);
//					map.put("type",type);
//					String deviceInfoObj = new Gson().toJson(map);

					deviceHandler.obtainMessage(
							DeviceSharedMSG.DEVICESIGN)
							.sendToTarget();

				}else{
					showShortToast("查询失败");
				}

			}
		});

	}

    /**
     * 签到
//     * @param info
     */
    public void signDevice() {
//        String sn = info.get("ksn").toString();
//        String type = info.get("type").toString();
        String mercid = AppApplication.getInstance().getUserno();

        HttpRequest.signDevice(deviceSn, type,mercid,latitudeStr,longitudeStr,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");


                if("000000".equals(dataMap.get("rspCd").toString())){

					Map<String, Object>  data = (Map<String, Object>) userData.get("data");
					switchingData = data.get("switchingData").toString();

                    deviceHandler.obtainMessage(
                            DeviceSharedMSG.DEVICESIGNDATA)
                            .sendToTarget();

                }else{
                    showShortToast("查询失败");
                }

            }
        });

    }


    /**
     * 绑定机具
//     * @param ksn
//     * @param bluetooth
//     * @param type
     */
	public void bindingDevice() {

		HttpRequest.bandingDevice(deviceSn, bluetoothName,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

//						Map<String, String> map = new HashMap<>();
//						map.put("ksn", deviceSn);
//						map.put("bluetooth",bluetooth);
//						map.put("type", type);
//						String deviceInfoObj = new Gson().toJson(map);

						deviceHandler.obtainMessage(DeviceSharedMSG.DEVICESIGNSUCCESS)
                            .sendToTarget();

				}else{
					showShortToast("查询失败");
				}

			}
		});
	}




	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
