package com.lovepig.lbs;

import java.util.ArrayList;

public class CellItem {
	public int cellId = 0;
	public int locationAreaCode = 0;
	public int mobileCountryCode = 0;
	public int mobileNetworkCode = 0;
	public int rssi = 0;
	public int radioType = 0;
	public String carrier = "";
	public ArrayList<CellNearItem> nearList = new ArrayList<CellNearItem>();

	public CellItem() {

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

	public ArrayList<CellNearItem> getNearList() {
		return nearList;
	}

	public void setNearList(ArrayList<CellNearItem> nearList) {
		this.nearList = nearList;
	}
	
	

}
