package com.haoxt.mpos.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.haoxt.mpos.model.CardInfo;
import com.haoxt.mpos.model.User;
import com.haoxt.mpos.view.CardInfoView;
import com.haoxt.mpos.view.UserView;

import tft.mpos.library.base.BaseAdapter;

/**用户adapter
 * @author baowen
 */
public class CardInfoAdapter extends BaseAdapter<CardInfo, CardInfoView> {
	//	private static final String TAG = "UserAdapter";

	public CardInfoAdapter(Activity context) {
		super(context);
	}

	@Override
	public CardInfoView createView(int position, ViewGroup parent) {
		return new CardInfoView(context, parent);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

}