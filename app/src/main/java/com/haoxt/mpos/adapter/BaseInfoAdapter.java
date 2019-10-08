package com.haoxt.mpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.google.zxing.common.StringUtils;
import com.haoxt.mpos.R;
import com.haoxt.mpos.model.BankInfo;

import java.util.ArrayList;
import java.util.HashMap;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.util.StringUtil;


public class BaseInfoAdapter extends BaseAdapter {

    private ArrayList<BankInfo> mArrayList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private BankInfo bankInfo;

    public BaseInfoAdapter(ArrayList<BankInfo> mArrayList, Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.base_info_message_item, null);
            mHolder.tvBankName = (TextView) convertView.findViewById(R.id.bank_item_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        bankInfo = mArrayList.get(position);
        mHolder.tvBankName.setText(bankInfo.getFldExp());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvBankName;
    }
}
