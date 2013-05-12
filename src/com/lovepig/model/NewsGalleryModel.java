package com.lovepig.model;

/**
 * 
 * 新闻类别，顶部左右滑动的类别
 * 
 */
public class NewsGalleryModel {
	/** 类别id */
	public int id;
	/** 类别是否被选中 1.选中 0未选中*/
	public int checked;
	/** 类别名称 */
	public String name;
	/** 类别日期 */
	public String mDate;
	public int indexNum;

	public NewsGalleryModel(int id, int checked, String name) {
		super();
		this.id = id;
		this.checked = checked;
		this.name = name;
	}

	public NewsGalleryModel() {
		super();

	}

}
