
package com.lovepig.model;

import com.lovepig.utils.Json;
public class NewsDetailModel {
	public String title;// 标题
	public String summary;// 简介
	public String order;
	public int id;
	public String iconPath;//新闻图片
	public String content;
	public String subTitle;
	public String imgUrl;
	public String cTime;
	public String cFrom;
    
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
        NewsDetailModel other = (NewsDetailModel) obj;
        if (id != other.id)
            return false;
        return true;
    }
    /**
     * {"id":1,"summary":"中新网5月10日
     * 表示强烈谴责。","iconUri":"http://www.dwz.cn/872bm","order":"10","titile":"事件经过：逃
     * 害台湾渔民应有公道。","top":"0"}
     * 
     *
     */
    public NewsDetailModel paserJson(Json json){
//    	this.id = json.getInt("id");
    	this.summary= json.getString("summary");
    	this.iconPath = json.getString("iconUri");
    	this.order=json.getString("order");
    	this.title = json.getString("title");
    	this.content = json.getString("content");
    	this.subTitle = json.getString("subTitle");
    	this.cTime = json.getString("cTime");
    	this.imgUrl = json.getString("imgUri");
    	this.cFrom = json.getString("from");
    	return this;
    }
}
