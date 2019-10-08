package com.haoxt.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.model.DeviceEntity;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends BaseAdapter {

    private List<DeviceEntity> deviceInfos;
//    private MyClickListener itemBtnOnClickListener;
    private LayoutInflater mInflater;

//    public DeviceAdapter(Context context, MyClickListener listener) {
//        mInflater = LayoutInflater.from(context);
//        deviceInfos = new ArrayList<>();
//        itemBtnOnClickListener = listener;
//    }

    public DeviceAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        deviceInfos = new ArrayList<>();
    }

    public void clear() {
        deviceInfos.clear();
        this.notifyDataSetChanged();
    }

    public void addDevice(DeviceEntity deviceInfo) {
        deviceInfos.add(deviceInfo);
        this.notifyDataSetChanged();
    }

    public DeviceEntity getDeviceInfo(int position) {
        return deviceInfos.get(position);
    }

    @Override
    public int getCount() {
        return deviceInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.base_info_message_item, null);
            holder = new ViewHolder();
            holder.device_item_name = (TextView) convertView.findViewById(R.id.bank_item_name);
//            holder.button = (Button) convertView.findViewById(R.id.btn);
//            holder.button.setText("绑定");
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        int index = findBluetoothDevice((ArrayList<DeviceEntity>) deviceInfos);
//        if (index >= 0 && null != deviceInfos.get(position).getName()) {
//            deviceInfos.remove(index);
//        }
        if (deviceInfos.size() > 0) {
            holder.device_item_name.setText(deviceInfos.get(position).getName());
//            holder.button.setOnClickListener(itemBtnOnClickListener);
//            holder.button.setTag(position);
        }
        return convertView;
    }

    private int findBluetoothDevice(ArrayList<DeviceEntity> deviceList) {
        for (int i = 0; i < deviceList.size(); i++) {
            String deviceMac = deviceList.get(i).getIdentifier();
            // 此处过滤掉以下mac地址的设备
            if (deviceMac.equals("11:33:77:44:60:34")
                    || deviceMac.equals("11:33:77:44:00:34")
                    || deviceMac.equals("28:00:00:00:60:43")
                    || deviceMac.equals("28:00:00:00:20:43")) {
                return i;
            }
        }
        return -1;
    }

    public class ViewHolder {
        public TextView device_item_name;
//        public Button button;
    }

//    /**
//     * 用于回调的抽象类
//     */
//    public static abstract class MyClickListener implements View.OnClickListener {
//        /**
//         * Called when a view has been clicked.
//         *
//         * @param v The view that was clicked.
//         */
//        @Override
//        public void onClick(View v) {
//            myOnClick((Integer) v.getTag(), v);
//        }
//
//        public abstract void myOnClick(int position, View v);
//    }
}
