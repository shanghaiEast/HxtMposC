
package com.haoxt.mpos.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.haoxt.mpos.R;
import com.haoxt.mpos.adapter.CardInfoAdapter;
import com.haoxt.mpos.model.CardInfo;
import com.haoxt.mpos.util.HttpRequest;
import com.haoxt.mpos.util.TestUtil;

import java.util.List;

import tft.mpos.library.base.BaseHttpListActivity;
import tft.mpos.library.interfaces.AdapterCallBack;
import tft.mpos.library.interfaces.OnBottomDragListener;
import tft.mpos.library.util.JSON;


/**
 *
 * 信用卡认证
 *
 * */

/**用户列表界面Activity示例
 * @author baowen
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoHttpListActivity.createIntent(...));
 */
public class CreditcardAuthenticateActivity extends BaseHttpListActivity<CardInfo, ListView, CardInfoAdapter> implements OnBottomDragListener {
	//	private static final String TAG = "DemoHttpListActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	public static final String INTENT_RANGE = "INTENT_RANGE";


	/**启动这个Activity的Intent
	 * @param context
	 * @param range
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, CreditcardAuthenticateActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private int range = RANGE_ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_http_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
		setContentView(R.layout.activity_creditcard_authenticate_list, this);

		intent = getIntent();
		range = intent.getIntExtra(INTENT_RANGE, range);


		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		srlBaseHttpList.autoRefresh();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		super.initView();

	}

	@Override
	public void setList(final List<CardInfo> list) {
		setList(new AdapterCallBack<CardInfoAdapter>() {

			@Override
			public CardInfoAdapter createAdapter() {
				return new CardInfoAdapter(context);
			}

			@Override
			public void refreshAdapter() {
				adapter.refresh(list);
			}
		});
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();

	}

	@Override
	public void getListAsync(final int page) {
		//实际使用时用这个，需要配置服务器地址		HttpRequest.getUserList(range, page, -page, this);

		//仅测试用<<<<<<<<<<<
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(TestUtil.getCardInfoList(page, 3)), null);
			}
		}, 1000);
		//仅测试用>>>>>>>>>>>>
	}

	@Override
	public List<CardInfo> parseArray(String json) {
		return JSON.parseArray(json, CardInfo.class);
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initEvent() {//必须调用
		super.initEvent();

	}


	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (id > 0) {
			toActivity(UpdateDebitCardActivity.createIntent(context));
		}
	}



	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}