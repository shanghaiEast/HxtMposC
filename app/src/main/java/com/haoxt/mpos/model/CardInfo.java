package com.haoxt.mpos.model;

import tft.mpos.library.base.BaseModel;

/**用户类
 * @author baowen
 */
public class CardInfo extends BaseModel {

	private static final long serialVersionUID = 1L;

	public static final int SEX_MAIL = 0;
	public static final int SEX_FEMALE = 1;
	public static final int SEX_UNKNOWN = 2;

	private String type; // 卡类型
	private String number; // 卡号
	private String bankName; //电话号码
	private String tag; //标签
	private boolean starred; //星标

	/**默认构造方法，JSON等解析时必须要有
	 */
	public CardInfo() {
		//default
	}
	public CardInfo(long id) {
		this();
		this.id = id;
	}
	public CardInfo(long id, String number) {
		this(id);
		this.number = number;
	}
	

	/**
	 * 以下getter和setter可以自动生成
	 * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
	 * <br>  android studio: 右键菜单 > Generate > Getter and Setter
	 */
	



	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return id > 0;// && StringUtil.isNotEmpty(phone, true);
	}

	@Override
	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
