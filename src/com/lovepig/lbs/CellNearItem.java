package com.lovepig.lbs;


public class CellNearItem{
	 private int cellId = 0;         
	 private int locationAreaCode = 0; 
	 private int mobileCountryCode = 0;    
	 private int mobileNetworkCode = 0;   
	 private int rssi = 0;    
	 private int radioType = 0; 
	 private int connected = 0;
	 private String carrier = "";
	 CellNearItem(){
		 
	 }
	public int getCellId() {
		return cellId;
	}
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}
	public int getLocationAreaCode() {
		return locationAreaCode;
	}
	public void setLocationAreaCode(int locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}
	public int getMobileCountryCode() {
		return mobileCountryCode;
	}
	public void setMobileCountryCode(int mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}
	public int getMobileNetworkCode() {
		return mobileNetworkCode;
	}
	public void setMobileNetworkCode(int mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getRadioType() {
		return radioType;
	}
	public void setRadioType(int radioType) {
		this.radioType = radioType;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public int getConnected() {
		return connected;
	}
	public void setConnected(int connected) {
		this.connected = connected;
	}
	 
}