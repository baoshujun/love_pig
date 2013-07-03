package com.lovepig.model;

import com.lovepig.utils.Json;

public class NewsDetailModel {
    public String title;// 标题
    public String summary;// 简介
    public String order;
    public int id;
    public String iconPath;// 新闻图片
    public String content;
    public String subTitle;
    public String imgUrl;
    public String cTime;
    public String cFrom;
    public boolean isFavorate;

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
     * {"content":
     * "2013年4月27日，北京养猪育种中心采用大跨度工艺、自动饲喂系统、空气过滤模式的1200头基础母猪原种场成功引进美系原种猪700头，该批种猪健康水平高；血统纯正、宽泛；体型外貌优美；生产性能优秀。此批种猪遗传资源的引进极大丰富了中心的品种资源，为中心种猪选育工作提供遗传素材，预计2014年3月起将为社会提供优质的种猪，欢迎广大新老用户到我中心考察、预订引种。&nbsp;"
     * ,"summary":"2013年4月27日，北京养猪育种中心采用大跨度工艺、自动饲喂系统、空气过滤模式","subTitle":null,
     * "title":"24行业北京养猪育种中心原种猪场引种成功","imgUri":
     * "http://i2.sinaimg.cn/dy/c/2013-07-01/U6795P1T1D27546726F21DT20130701182712.jpg"
     * ,"from":"本站","cTime":"2013-07-02"}
     * 
     * 
     */
    public NewsDetailModel paserJson(Json json) {
        if (json.has("content")) {
            this.content = json.getString("content");
        }
        if (json.has("summary")) {
            this.summary = json.getString("summary");
        }
        if (json.has("subTitle")) {
            this.subTitle = json.getString("subTitle");
        }
        if (json.has("title")) {
            this.title = json.getString("title");
        }
        if (json.has("imgUri")) {
            this.imgUrl = json.getString("imgUri");
        }
        if (json.has("from")) {
            this.cFrom = json.getString("from");
        }
        if (json.has("cTime")) {
            this.cTime = json.getString("cTime");
        }
        return this;
    }
}
