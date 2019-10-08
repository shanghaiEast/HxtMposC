package com.haoxt.mpos.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.haoxt.mpos.model.ItemCardInfo;
import com.haoxt.mpos.view.CardInfoView;

import tft.mpos.library.base.BaseAdapter;

/**用户adapter
 * @author baowen
 */
public class CardInfoAdapter extends BaseAdapter<ItemCardInfo, CardInfoView> {
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