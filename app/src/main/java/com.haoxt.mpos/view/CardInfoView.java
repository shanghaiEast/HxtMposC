/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.haoxt.mpos.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.haoxt.mpos.R;
import com.haoxt.mpos.model.CardInfo;
import com.haoxt.mpos.model.User;

import tft.mpos.library.base.BaseModel;
import tft.mpos.library.base.BaseView;
import tft.mpos.library.ui.WebViewActivity;
import tft.mpos.library.util.CommonUtil;
import tft.mpos.library.util.StringUtil;

/**用户View
 * @author Lemon
 * @use
 * <br> UserView userView = new UserView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = userView.createView(inflater);
 * <br> userView.bindView(position, data);
 * <br> 或  其它类中使用:
 * <br> containerView.addView(userView.createView(inflater));
 * <br> userView.bindView(data);
 * <br> 然后
 * <br> userView.setOnDataChangedListener(onDataChangedListener);data = userView.getData();//非必需
 * <br> userView.setOnClickListener(onClickListener);//非必需
 */
public class CardInfoView extends BaseView<CardInfo> implements OnClickListener {
	private static final String TAG = "UserView";

	public CardInfoView(Activity context, ViewGroup parent) {
		super(context, R.layout.card_info_item, parent);
	}

	public TextView tvBankName,tvBankCardType,tvBankNo;

	@SuppressLint("InflateParams")
	@Override
	public View createView() {

		tvBankName = findView(R.id.tvBankName);
		tvBankCardType = findView(R.id.tvBankCardType);
		tvBankNo = findView(R.id.tvBankNo);

		return super.createView();
	}

	@Override
	public void bindView(CardInfo data_){
		super.bindView(data_ != null ? data_ : new CardInfo());

		tvBankName.setText("ID:" + data.getBankName());
		tvBankCardType.setText("ID:" + data.getType());
		tvBankNo.setText("ID:" + data.getNumber());
	}

	@Override
	public void onClick(View v) {
		if (BaseModel.isCorrect(data) == false) {
			return;
		}
		switch (v.getId()) {
		case R.id.ivUserViewHead:
//			toActivity(WebViewActivity.createIntent(context, data.getName(), data.getHead()));
			break;
		default:

			bindView(data);
			break;
		}
	}
}