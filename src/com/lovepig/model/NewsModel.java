package com.lovepig.model;

import java.util.ArrayList;

import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

import android.graphics.Bitmap;


public class NewsModel {
	public String title;// 标题
	public String subTitle;//副标题
	public String summary;// 简介
	public String editor;// 来自
	public String order;
	public String updateTime;//更新时间
	public String categoiresId;//分类id
	public int id;
	public String from;//出自
	public String content;
	
	
	
    public int commentSize;
    public Bitmap mImageView;
    public String createTime;// 创建时间 ，发表时间
    public String details;// 内容
    public String iconPath;// 新闻图片
    public String imgPath;//新闻大图
    public String picintro;// 图片介绍
    public int commentNum;// 评论数
    public boolean isTop;//是否是头条
    public ArrayList<NewsModel> topNews;//头条列表
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewsModel other = (NewsModel) obj;
        if (id != other.id)
            return false;
        return true;
    }
    /**
     * {"title":"图解新式军车号牌：采用电子标签核对军车真伪","subTitle":"军车号牌的悬挂要求",
     * "summary":"大车号牌、小车号牌不得混用；军车号牌、《行车执照》和ETC电子标签，必须与车辆信息相符；
     * 号牌不得挪用，不得擅自变更使用；临时号牌有效期最长不超过一个月。","editor":"解放军报",
     * "order":"100","imgPath":"http://i3.sinaimg.cn/jc/2013-04-28/U6449P27T1D723178F3DT20130428084241.jpg",
     * "iconPath":"http://i3.sinaimg.cn/jc/2013-04-28/U6449P27T1D723178F3DT20130428084241.jpg",
     * "updateTime":"1367143949899","createTime":"1367143949899",
     * "categoiresId":"1","id":"1","from":"新浪新闻","content":null}]}
     * 
     *
     */
    public NewsModel paserJson(Json json){
    	 
    	 this.title = json.getString("title");
    	 this.subTitle= json.getString("subTitle");
    	 this.summary= json.getString("summary");
    	 this.editor=json.getString("editor");
    	 this.order=json.getString("order");
    	 this.imgPath=json.getString("imgPath");
         this.id = json.getInt("id");
         this.createTime = json.getString("createTime");
         this.iconPath = json.getString("iconPath");
         this.updateTime = json.getString("updateTime");
         this.categoiresId = json.getString("categoiresId");
         this.from = json.getString("from");
         this.categoiresId = json.getString("categoiresId");
         //this.isTop = json.getInt("isTop") == 1;
         if (json.has("content")) {
             this.content = json.getString("content");
         }
        
         
    	return this;
    }
  
}
