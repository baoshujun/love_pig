package com.lovepig.model;

import java.util.ArrayList;

import android.graphics.Bitmap;
public class NewsModel {
    public int commentSize;
    public int id;
    public Bitmap mImageView;
    public String time;// 发表时间
    public String title;// 标题
    public String intro;// 简介
    public String publisher;// 来自
    public String details;// 内容
    public String picurl;// 新闻图片
    public String bigpicurl;//新闻大图
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
  
}
