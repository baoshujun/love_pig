package com.lovepig.model;

import com.lovepig.utils.Json;

public class DevicesManuModel {

    /**
     * 编号
     */
    public String id;
    /**
     * 编号
     */
    public String name;
    /**
     * 品牌
     */
    public String brandId;
    /**
     * 分类
     */
    public String catId;
    /**
     * 产品简介
     */
    public String des;
    /**
     * 备注
     */
    public String speci;
    public String remark;
    public String params;
    /**
     * 推荐指数
     */
    public String rating;
    public String manuId;
    public String updateTime;
    public String createTiem;
    public String iconUri;
    /**
     * 价格
     */
    public String price;

    /**
     * {"price":[{"id":"1","name":"产床","brandId":"233", "catId":"44","des":"
     * 好未来。 ","speci":"个","remark":"","params":"","rating":"80","manuId":"242","
     * updateTime
     * ":"1367143949899","createTiem":"1367143949899","iconUri":"http:/
     * /img11.hc360 .cn/11/busin/166/059/b/11-166059341.jpg","price":"7000"}\
     */
    public DevicesManuModel parserJson(Json json) {
        if (json.has("id")) {
            this.id = json.getString("id");
        }
        if (json.has("name")) {
            this.name = json.getString("name");
        }
        if (json.has("brandId")) {
            this.brandId = json.getString("brandId");
        }

        if (json.has("catId")) {
            this.catId = json.getString("catId");
        }

        if (json.has("des")) {
            this.des = json.getString("des");
        }
        if (json.has("speci")) {
            this.speci = json.getString("speci");
        }
        if (json.has("remark")) {
            this.remark = json.getString("remark");
        }
        if (json.has("params")) {
            this.params = json.getString("params");
        }

        if (json.has("rating")) {
            this.rating = json.getString("rating");
        }
        if (json.has("manuId")) {
            this.manuId = json.getString("manuId");
        }
        if (json.has("brandId")) {
            this.brandId = json.getString("brandId");
        }

        if (json.has("updateTime")) {
            this.updateTime = json.getString("updateTime");
        }

        if (json.has("createTiem")) {
            this.createTiem = json.getString("createTiem");
        }
        if (json.has("speci")) {
            this.speci = json.getString("speci");
        }
        if (json.has("iconUri")) {
            this.iconUri = json.getString("iconUri");
        }
        if (json.has("price")) {
            this.price = json.getString("price");
        }
        return this;
    }

}
