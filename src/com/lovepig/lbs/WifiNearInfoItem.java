package com.lovepig.lbs;


public class WifiNearInfoItem {

	private String nBssid = "";
	private int signal_strength = 0;
	private int connected = 0;
	
	public String getnBssid() {
		return nBssid;
	}
	public void setnBssid(String nBssid) {
		this.nBssid = nBssid;
	}
	public int getSignal_strength() {
		return signal_strength;
	}
	public void setSignal_strength(int signal_strength) {
		this.signal_strength = signal_strength;
	}
	public int getConnected() {
		return connected;
	}
	public void setConnected(int connected) {
		this.connected = connected;
	}
	
	
}
