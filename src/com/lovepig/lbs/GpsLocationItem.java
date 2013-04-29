package com.lovepig.lbs;

public class GpsLocationItem {

	private double lat = 0;//纬度
	private double lng = 0;//经度
    //private double altitude = 0;//海拔高度
	private float accuracy = 0;//精确度
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	
	
}
