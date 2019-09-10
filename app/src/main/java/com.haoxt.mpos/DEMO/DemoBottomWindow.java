package com.haoxt.mpos.DEMO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.haoxt.mpos.R;
import com.haoxt.mpos.activity.UserActivity;
import tft.mpos.library.base.BaseViewBottomWindow;
import tft.mpos.library.model.Entry;


/** 使用方法：复制>粘贴>改名>改代码 */
/**底部弹出窗口界面示例
 * @author baowen
 * <br> toActivity或startActivityForResult (DemoBottomWindow.createIntent(...), requestCode);
 * <br> 然后在onActivityResult方法内
 * <br> data.getStringExtra(DemoBottomWindow.RESULT_DATA); 可得到返回值
 */
public class DemoBottomWindow extends BaseViewBottomWindow<Entry<String, String>, DemoComplexView> implements OnClickListener {
	private static final String TAG = "DemoBottomWindow";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static Intent createIntent(Context context, String title) {
		return new Intent(context, DemoBottomWindow.class).putExtra(INTENT_TITLE, title);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		super.initView();

	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();

		data = new Entry<String, String>("Activity", TAG);
		data.setId(1);

		containerView.bindView(data);
	}

	@Override
	public String getTitleName() {
		return "Demo";
	}
	@Override
	public String getReturnName() {
		return null;
	}
	@Override
	public String getForwardName() {
		return null;
	}

	@Override
	protected DemoComplexView createView() {
		return new DemoComplexView(context);
	}

	@Override
	protected void setResult() {
		//示例代码<<<<<<<<<<<<<<<<<<<
		setResult(RESULT_OK, new Intent().putExtra(RESULT_DATA, TAG + " saved"));
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用
		super.initEvent();

		containerView.ivDemoComplexViewHead.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivDemoViewHead:
			if (data != null) {
				toActivity(UserActivity.createIntent(context, data.getId()));
			}
			break;
		default:
			break;
		}
	}




	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}