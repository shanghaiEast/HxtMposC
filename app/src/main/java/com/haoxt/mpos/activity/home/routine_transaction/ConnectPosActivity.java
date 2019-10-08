package com.haoxt.mpos.activity.home.routine_transaction;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.home.DeviceBindActivity;
import com.haoxt.mpos.activity.home.DeviceBindResultActivity;
import com.haoxt.mpos.adapter.DeviceAdapter;
import com.haoxt.mpos.application.AppApplication;
import com.haoxt.mpos.fragment.tab.MainTabActivity;
import com.haoxt.mpos.model.DeviceEntity;
import com.haoxt.mpos.util.DateUtil;
import com.haoxt.mpos.util.DeviceSharedMSG;
import com.haoxt.mpos.util.HttpRequest;
import com.haoxt.mpos.util.TYDeviceDelegate;
import com.whty.bluetooth.manage.util.BluetoothStruct;
import com.whty.bluetoothsdk.util.Utils;
import com.whty.comm.inter.ICommunication;
import com.whty.tymposapi.DeviceApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

import static com.whty.usb.util.USBUtil.deviceConnected;
import static tft.mpos.library.util.CommonUtil.toActivity;

/**
 * 连接POS页面
 */
public class ConnectPosActivity extends BaseActivity implements View.OnClickListener {

    private SignaturePopup mDialog;
    private ImageView mBackIv;
    private ImageView mConnectedIv;
    private LinearLayout mPosItemLy;
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectPosActivity.DeviceHandler deviceHandler;
    private TYDeviceDelegate deviceDelegate;
    private DeviceApi deviceApi;
    private BroadcastReceiver receiver = null;
    private boolean isInited = false;
    private boolean deviceConnected = false;
    public static ArrayList<BluetoothStruct> items = new ArrayList<>();
    public static ArrayList<DeviceEntity> devices = new ArrayList<>();
    private LinkedTreeMap<String, String> cardInfo;
    private HashMap<String, String> deviceInfo;
    private SignaturePopup mSignaturePopup;
//    private String latitudeStr,longitudeStr,deviceSn,bluetoothName,snTypNo,switchingData,ksn,pin,feild59;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ConnectPosActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_connect_pos);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        initDevice();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    private void initDevice(){

        deviceHandler = new ConnectPosActivity.DeviceHandler();
        deviceDelegate = new TYDeviceDelegate(deviceHandler);
        deviceApi = new DeviceApi(context);
        deviceApi.setDelegate(deviceDelegate);

//        if("".equals(AppApplication.getInstance().getBluetooth())){
//            IntentFilter intent = new IntentFilter();
//            intent.addAction(BluetoothDevice.ACTION_FOUND);
//            intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//            intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//            intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//            intent.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//            intent.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
//            intent.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
//            intent.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
//            intent.setPriority(-1000);
//
//            context.registerReceiver(broadcastReceiver, intent);
//            if (!isInited) {
//                if (deviceApi.initDevice(ICommunication.BLUETOOTH_DEVICE)) {
//                    isInited = true;
//                } else {
//                }
//            }
//            if (!deviceConnected) {
//                btAdapter.cancelDiscovery();
//                btAdapter.startDiscovery();
//
//                // BlueToothUtil.items.clear();
//                items.clear();
//                devices.clear();
//            } else {
//                Toast.makeText(context,
//                        "", Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//        }else{
//                if (!isInited) {
//                    if (deviceApi.initDevice(ICommunication.BLUETOOTH_DEVICE)) {
//                        isInited = true;
//                    } else {
//                    }
//                }
//              connectDevice("test");
//        }
        if (!isInited) {
                if (deviceApi.initDevice(ICommunication.BLUETOOTH_DEVICE)) {
                    isInited = true;
                } else {
                }
            }

        connectDevice("test");

    }

    @Override
    public void initView() {
        mBackIv = findView(R.id.back_iv);
        mConnectedIv = findView(R.id.connected_iv);
        mPosItemLy = findView(R.id.pos_item_ly);
    }

    private HashMap<String, Object> pageData;
    @Override
    public void initData() {
        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");
        deviceInfo = new HashMap<String, String>();
    }

    @Override
    public void initEvent() {
        mPosItemLy.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
//        PayActivity activity = (PayActivity) getActivity();
        switch (view.getId()) {
            case R.id.back_iv:
//                activity.onBackPressed();
                break;
            case R.id.pos_item_ly:
                showPop();
                break;

        }

    }

    public void showPop() {
        if (mDialog != null)
            mDialog = null;
        mDialog = new SignaturePopup(context, pageData);
        mDialog.show();
    }

//    /**
//	 * 实现类，响应按钮点击事件
//	 */
//    SignaturePopup.MyClickListener mListener = new SignaturePopup.MyClickListener() {
//		@Override
//		public void myOnClick(int position, View v) {
//
//
//		}
//	};

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

                case DeviceSharedMSG.GETDEVICESN:               //获取SN
//                    getKsn();
                    break;

                case DeviceSharedMSG.GETDEVICEKSN:               //获取KSN
                    getKsn();
                    break;

                case DeviceSharedMSG.STARTREADCARD:				//开始刷卡
                    startSwiper("1","","0x00","0x64","0x07");
                    break;

                case DeviceSharedMSG.WAITINGCARD:				//等待刷卡
                    show_msg = (String) msg.obj;
                    Log.d("等待刷卡--------->", show_msg);
                    break;

                case DeviceSharedMSG.READCARD_SUCCESS:			//读卡成功
                    show_msg = (String) msg.obj;
                    Log.d("读卡成功--------->", show_msg);
                    break;

                case DeviceSharedMSG.READCARD_FAIL:				//读卡失败
                    show_msg = (String) msg.obj;
                    Log.d("读卡失败--------->", show_msg);
                    break;

                case DeviceSharedMSG.READCARDDATA_SUCCESS:		//获取卡数据
                    show_msg = (String) msg.obj;
                    cardInfo = (LinkedTreeMap<String, String>) StringUtil.json2mapString(show_msg);
                    getDeviceInfo();
                    Log.d("获取卡数据--------->", cardInfo.toString());
                    break;

                case DeviceSharedMSG.GETPINBLOCKSATRT:               //  获取pinblock
                    getPin();
                    break;

                case DeviceSharedMSG.GETPINBLOCK:               //  获取pinblock
                    getField59();
                    break;


//                case DeviceSharedMSG.GETFIELD59:               //获取59
//                    getField59();
//                    break;

                case DeviceSharedMSG.UPLOADTRANSATIONINFO:    //上传交易
                    uploadtransation();
                    break;

                case DeviceSharedMSG.UPLOADTRANSATIONINFOSUCESS:		//DOWNGRADETRANSACTION
                    showPop();
                    break;

                case DeviceSharedMSG.DOWNGRADETRANSACTION:		//DOWNGRADETRANSACTION
                    show_msg = (String) msg.obj;
                    Log.d("--------->", show_msg);
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
     * 设备连接
     *
     * @param adress
     */
    public void connectDevice(final String adress) {
        if(!adress.equals("")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    deviceApi.connectDevice("74:F8:DB:A7:21:36");
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
                HashMap deviceInfo1 = deviceApi.getDeviceIdentifyInfo();
                Log.e("Www", "getDeviceInfo----" + deviceInfo);
                if (deviceInfo != null) {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("ksn", (String) deviceInfo.get("ksn"));
//                    map.put("factor", (String) deviceInfo.get("factor"));
//                    map.put("cipher", (String) deviceInfo.get("cipher"));

                    deviceInfo.put("snSeq",(String) deviceInfo1.get("ksn"));
                    deviceInfo.put("randomKey",(String) deviceInfo1.get("factor"));
                    deviceInfo.put("hdSeqData",(String) deviceInfo1.get("cipher"));

                    deviceHandler.obtainMessage(
                            DeviceSharedMSG.GETPINBLOCKSATRT)
                            .sendToTarget();
                }
            }
        }).start();
    }

    /**
     * 获取KSN号
     */

    public void getKsn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap deviceKSNInfo = deviceApi.getDeviceKSNInfo();

                String snTypNo = deviceKSNInfo.get("deviceType").toString();
                String ksn = deviceKSNInfo.get("ksn").toString();

                deviceInfo.put("snTypNo",snTypNo);
                deviceInfo.put("snSeq",ksn);

                Log.e("www", "getKsn----" + deviceKSNInfo);
                if (deviceKSNInfo != null) {

//					Map<String, String> map = new HashMap<>();
//					map.put("type", (String) deviceKSNInfo.get("deviceType"));
//					String deviceInfoObj = new Gson().toJson(map);

                    deviceHandler.obtainMessage(
                            DeviceSharedMSG.STARTREADCARD)
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
                String deviceSn = deviceApi.getDeviceSN();
                deviceInfo.put("snNo",deviceSn);
                deviceHandler.obtainMessage(
                        DeviceSharedMSG.GETDEVICEKSN)
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                String Feild59tx = deviceApi.getIso8583Feild59();
                deviceInfo.put("feild59",Feild59tx);
                deviceHandler.obtainMessage(
                        DeviceSharedMSG.UPLOADTRANSATIONINFO)
                        .sendToTarget();

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
                HashMap<String, Object>  encPinblock = deviceApi.getEncPinblock(null, (byte) 0, null);
                String pin  =   encPinblock.get("pin").toString();
                deviceInfo.put("pin",pin);
            }
        }).start();
    }

    /**
     * 交易提交
     */
    public void confirmTransaction() {
        deviceApi = new DeviceApi(context);
        deviceApi.setDelegate(deviceDelegate);
        new Thread(new Runnable() {
            @Override
            public void run() {
                deviceApi.confirmTransaction();
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



    private int findBluetoothDevice(String mac,
                                    ArrayList<BluetoothStruct> deviceList) {
        for (int i = 0; i < deviceList.size(); i++) {
            if (((BluetoothStruct) deviceList.get(i)).getMac().equals(mac))
                return i;
        }
        return -1;
    }

    /**
     * 上送交易
     */
    public void uploadtransation() {
        String transType = "purchase";
        String snNo = deviceInfo.get("snNo");
        String snTypNo = deviceInfo.get("snTypNo");
        String mercId = AppApplication.getInstance().getMerchantId();
        String pan = cardInfo.get("pan");
        String amount = pageData.get("amount").toString();
        String dateExpiration = cardInfo.get("dateExpiration");
        String posEntryModeCode =  cardInfo.get("posEntryModeCode");
        String cardSequenceNumber = cardInfo.get("cardSequenceNumber");
        String track2 = cardInfo.get("track2");
        String track3 = cardInfo.get("track3");
        String pin = deviceInfo.get("pin");
        String ICSystemRelated = cardInfo.get("ICSystemRelated");

        String ksn =  deviceInfo.get("snSeq") ;
        String randomKey = deviceInfo.get("randomKey") ;
        String hdSeqData = deviceInfo.get("hdSeqData");


        String snNetWorkNo = "";
        String longitude = pageData.get("longitude").toString();
        String latitude = pageData.get("latitude").toString();
        String secretFree = "0";
        String actPayType = "0";
        String provNm = pageData.get("province").toString();
        String cityNm = pageData.get("city").toString();;
        String areaNm = pageData.get("district").toString();;


        HttpRequest.posTransation(transType, snNo,snTypNo,mercId,pan,amount,dateExpiration,posEntryModeCode,cardSequenceNumber,track2,track3,pin,
                ICSystemRelated, ksn,randomKey,hdSeqData,snNetWorkNo,longitude,latitude,secretFree,actPayType,provNm,cityNm,areaNm,
                0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
                Map<String, Object> data = (Map<String, Object>) userData.get("data");

                if("000000".equals(dataMap.get("rspCd").toString())&&data.get("responseCode").toString()=="00"){

                    pageData.put("sourceTranDate",data.get("sourceTranDate")==null?"":data.get("sourceTranDate").toString());
                    pageData.put("sourcePosRequestId",data.get("sourcePosRequestId")==null?"":data.get("sourcePosRequestId").toString());
                    pageData.put("sourceBatchNo",data.get("sourceBatchNo")==null?"":data.get("sourceBatchNo").toString());
                    pageData.put("systemsTraceAuditNumber",data.get("systemsTraceAuditNumber")==null?"":data.get("systemsTraceAuditNumber").toString());
                    pageData.put("batchNo",data.get("batchNo")==null?"":data.get("batchNo").toString());
                    pageData.put("cardAcceptorTerminalId",data.get("cardAcceptorTerminalId")==null?"":data.get("cardAcceptorTerminalId").toString());

                    deviceHandler.obtainMessage(
                            DeviceSharedMSG.UPLOADTRANSATIONINFOSUCESS)
                            .sendToTarget();

//                    Intent intent = new Intent(context, SalesSlipActivity.class);
//                    intent.putExtra("pageData",(Serializable)pageData);
//                    toActivity(intent);

                }else{
                    showShortToast("登陆失败");
                }

            }
        });
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

                    if(bluetoothDevice.getName().equals(AppApplication.getInstance().getBluetooth())){
                        connectDevice(bluetoothDevice.getAddress());

                    }
                }
            }

        }
    };
}
