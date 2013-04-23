package com.lovepig.model;

import java.util.ArrayList;
/**
 * 在线音乐类别实体
 * @author bao.shujun@audiocn.com
 *
 */
public class CategoryModel extends Model{
    
	public CategoryModel() {
        super();
     
    }
    public CategoryModel(String id) {
        super(id);
    }
    public CategoryModel(String id,String name,int childType) {
        super(id);
        this.name=name;
        this.childType=childType;
    }
    public int childType;
	/**
	 * 子类
	 */
	public ArrayList<Model> child;
	/**
	 *是否有封面
	 */
	public boolean isCover;
	/**
	 *封面urlַ
	 */
	public String coverUrl;
	public boolean isShowIndex;
	

}
