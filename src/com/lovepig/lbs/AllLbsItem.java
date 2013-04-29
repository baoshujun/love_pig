package com.lovepig.lbs;

public class AllLbsItem {
	private GpsLocationItem gli;
	private AGpsLocationItem agli;
	private WifiInfoItem wii;
	private CellItem ci;

	public AllLbsItem() {
	}

	public GpsLocationItem getGli() {
		return gli;
	}

	public void setGli(GpsLocationItem gli) {
		this.gli = gli;
	}

	public AGpsLocationItem getAgli() {
		return agli;
	}

	public void setAgli(AGpsLocationItem agli) {
		this.agli = agli;
	}

	public WifiInfoItem getWii() {
		return wii;
	}

	public void setWii(WifiInfoItem wii) {
		this.wii = wii;
	}

	public CellItem getCi() {
		return ci;
	}

	public void setCi(CellItem ci) {
		this.ci = ci;
	}

}
