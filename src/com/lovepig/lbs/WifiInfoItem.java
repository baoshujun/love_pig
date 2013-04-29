package com.lovepig.lbs;

import java.util.ArrayList;

public class WifiInfoItem {
	private String myBssid = "";
	private ArrayList<WifiNearInfoItem> nearList = new ArrayList<WifiNearInfoItem>();
	private int signal_strength = 0;

	public WifiInfoItem() {
	}

	public String getMyBssid() {
		return myBssid;
	}

	public void setMyBssid(String myBssid) {
		this.myBssid = myBssid;
	}

	public ArrayList<WifiNearInfoItem> getNearList() {
		return nearList;
	}

	public void setNearList(ArrayList<WifiNearInfoItem> nearList) {
		this.nearList = nearList;
	}

	public int getSignal_strength() {
		return signal_strength;
	}

	public void setSignal_strength(int signal_strength) {
		this.signal_strength = signal_strength;
	}
	
	
}
