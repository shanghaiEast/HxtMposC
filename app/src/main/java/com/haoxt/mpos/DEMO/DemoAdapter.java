package com.haoxt.mpos.DEMO;

import android.app.Activity;
import android.view.ViewGroup;

import tft.mpos.library.base.BaseAdapter;
import tft.mpos.library.model.Entry;


/** 使用方法：复制>粘贴>改名>改代码 */
/**adapter模板
 * <br> 适用于几乎所有列表、表格，包括：
 * <br> 1.RecyclerView及其子类
 * <br> 2.ListView,GridView等AbsListView的子类
 * @author baowen
 * @use 修改.ItemView代码 >> new DemoAdapter(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter extends BaseAdapter<Entry<String, String>, DemoView> {

	public DemoAdapter(Activity context) {
		super(context);
	}

	@Override
	public DemoView createView(int position, ViewGroup parent) {
		return new DemoView(context, parent);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}


}
