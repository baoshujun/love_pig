package com.lovepig.model;

public class DownModel extends LocalModel{
    /**
     * 只在内存中赋值,不持久化到数据库
     * 为mv的kalaok设置3个url,其余只使用url[0]
     * url[0]=原唱
     * url[1]=伴唱
     * url[2]=视频
     */
	public String url[]=new String[3];
	/**
     * 只在内存中赋值,不持久化到数据库
     */
	public String lrcUrl;
	/**
     * 总大小,单位byte
     */
	public int duration;
	/**
     * 已下载大小,单位byte
     */
	public int position;
	/**
     * 0=停止,1=下载,2=等待，3=连接中，4=错误
     */
	public int state;
}
