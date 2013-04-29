package com.lovepig.lbs;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
//import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class LocationAssistant {

	private LocationManager locationManager;
	private String provider;
	private static final String tag = "LocationAssistant";
	private Context mContext;

	private TelephonyManager telephonyManager;

	public LocationAssistant(Context context) {
		this.mContext = context;
		locationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	public AllLbsItem getAllLbsInfo() {
		AllLbsItem ali = new AllLbsItem();
		/*int networkType = telephonyManager.getNetworkType();
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
			ali.setCi(getGsmCellInfo());
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
			ali.setCi(getCdmaCellInfo());
			break;
		}*/
		
		int phoneType = telephonyManager.getPhoneType();
		switch(phoneType){
		case TelephonyManager.PHONE_TYPE_NONE:
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			ali.setCi(getGsmCellInfo());
			break;
			//TODO
//		case TelephonyManager.PHONE_TYPE_CDMA:
//			ali.setCi(getCdmaCellInfo());
//			break;
		default:
			ali.setCi(getGsmCellInfo());
			break;
		}
		ali.setAgli(getAGpsInfo());
		ali.setGli(getGpsInfo());
		ali.setWii(getWifiInfo());
		return ali;
	}

	private Location getLocationViaGps() {

		Location gpsLocation = null;
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(true);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setSpeedRequired(true);

		provider = locationManager.getBestProvider(criteria, true);
		try {
			gpsLocation = locationManager.getLastKnownLocation(provider);
		} catch (Exception e) {
			Log.d(tag, "Exception occur : " + e.getMessage());
		}

		return gpsLocation;
	}

	private Location getLocationViaGps(String gpsType) {
		Location gpsLocation = null;
		if (!gpsType.equals(LocationManager.GPS_PROVIDER)
				&& !gpsType.equals(LocationManager.NETWORK_PROVIDER))
			return null;

		provider = gpsType;
		try {
			gpsLocation = locationManager.getLastKnownLocation(provider);
		} catch (Exception e) {
			Log.d(tag, "Exception occur : " + e.getMessage());
		}
		return gpsLocation;
	}

	private GpsLocationItem getLocViaGps(String gpsType) {
		GpsLocationItem gli = new GpsLocationItem();
		Location l = getLocationViaGps(gpsType);
		gli.setLat(l.getLatitude());// 纬度
		gli.setLng(l.getLongitude());// 经度
		gli.setAccuracy(l.getAccuracy());// 精确度

		return gli;
	}

	private GpsLocationItem getGpsInfo() {
		GpsLocationItem gli = new GpsLocationItem();
		String provider = LocationManager.GPS_PROVIDER;
		Location l = getLocationViaGps(provider);
		if (l == null)
			return null;
		gli.setLat(l.getLatitude());// 纬度
		gli.setLng(l.getLongitude());// 经度
		gli.setAccuracy(l.getAccuracy());// 精确度
		return gli;

	}

	private AGpsLocationItem getAGpsInfo() {
		AGpsLocationItem agli = new AGpsLocationItem();
		String provider = LocationManager.NETWORK_PROVIDER;
		Location l = getLocationViaGps(provider);
		if (l == null)
			return null;
		agli.setLat(l.getLatitude());// 纬度
		agli.setLng(l.getLongitude());// 经度
		agli.setAccuracy(l.getAccuracy());// 精确度
		return agli;

	}

	// TODO
//	private CellItem getCdmaCellInfo() {
//		CellItem cci = new CellItem();
//		int simState = telephonyManager.getSimState();
//		if(simState == TelephonyManager.SIM_STATE_READY){
//			CdmaCellLocation location = (CdmaCellLocation) telephonyManager
//					.getCellLocation();
//			telephonyManager.getNetworkOperatorName();
//			if (location == null)
//				return null;
//			int sid = location.getSystemId();// 系统标识 mobileNetworkCode
//			int bid = location.getBaseStationId();// 基站小区号 cellId
//			int nid = location.getNetworkId();// 网络标识 locationAreaCode
//			String operatorName = telephonyManager.getNetworkOperatorName();
//	
//			cci.setCellId(bid);
//			cci.setMobileNetworkCode(sid);
//			cci.setLocationAreaCode(nid);
//			cci.setMobileCountryCode(Integer.parseInt(telephonyManager
//					.getNetworkOperator().substring(0, 3)));
//			cci.setRadioType(telephonyManager.getPhoneType());
//			cci.setCarrier(operatorName);
//	
//			List<NeighboringCellInfo> nearList = telephonyManager
//					.getNeighboringCellInfo();
//			if (nearList != null) {
//				int len = nearList.size();
//				for (int i = 0; i < len; i++) {
//					NeighboringCellInfo nhci = nearList.get(i);
//					int cid = nhci.getCid();
//					int lac = nhci.getLac();
//					CellNearItem cnci = new CellNearItem();
//					cnci.setCellId(cid);
//					cnci.setLocationAreaCode(lac);
//					cnci.setRadioType(nhci.getNetworkType());
//					cnci.setRssi(nhci.getRssi());
//				}
//			}
//		}
//		return cci;
//	}

	private CellItem getGsmCellInfo() {
		CellItem gci = null;
		int simState = telephonyManager.getSimState();
		if(simState == TelephonyManager.SIM_STATE_READY){
			gci = new CellItem();
			GsmCellLocation location = (GsmCellLocation) telephonyManager
					.getCellLocation();
			if (location == null)
				return null;
			int cid = location.getCid();
			int lac = location.getLac();
			int mcc = Integer.valueOf(telephonyManager.getNetworkOperator()
					.substring(0, 3));
			int mnc = Integer.valueOf(telephonyManager.getNetworkOperator()
					.substring(3, 5));
			int netType = telephonyManager.getPhoneType();
			String operatorName = telephonyManager.getNetworkOperatorName();
	
			gci.setCellId(cid);
			gci.setLocationAreaCode(lac);
			gci.setMobileCountryCode(mcc);
			gci.setMobileNetworkCode(mnc);
			gci.setCarrier(operatorName);
			gci.setRadioType(netType);
	
			List<NeighboringCellInfo> l = telephonyManager.getNeighboringCellInfo();
			if (l != null) {
				int len = l.size();
				for (int i = 0; i < len; i++) {
					int cellId = l.get(i).getCid();
					// TODO
//					int loac = l.get(i).getLac();
//					int type = l.get(i).getNetworkType();
					// TODO
					int loac = 0;
					int type = 0;
					int rssi = l.get(i).getRssi();
					CellNearItem gnci = new CellNearItem();
					gnci.setCellId(cellId);
					gnci.setLocationAreaCode(loac);
					gnci.setRadioType(type);
					gnci.setRssi(rssi);
					gci.nearList.add(gnci);
				}
			}
		}
		return gci;
	}

	private WifiInfoItem getWifiInfo() {
		ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		WifiManager wifi_service = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		if (mWifi.isConnected()) {
			WifiInfoItem wi = new WifiInfoItem();

			WifiInfo wifiinfo = wifi_service.getConnectionInfo();
			String bssid = wifiinfo.getBSSID();
			int strength = wifiinfo.getRssi();
			wi.setMyBssid(bssid);
			wi.setSignal_strength(strength);
			Log.d(tag, "bssid" + ":" + bssid + "\r\n");List<ScanResult> scanResult = wifi_service.getScanResults();
			
			if(scanResult != null){
				int size = scanResult.size();
				for (int i = 0; i < size; i++) {
					ScanResult sr = scanResult.get(i);
					if(!bssid.equals(sr.BSSID)){
					WifiNearInfoItem wnii = new WifiNearInfoItem();
					String nBssid = sr.BSSID;
					int level = sr.level;
					wnii.setnBssid(nBssid);
					wnii.setSignal_strength(level);
					wi.getNearList().add(wnii);
					Log.d(tag, "near bssid" + nBssid);
					}
				}
			}
			return wi;
		}else{
			return null;
		}
	}

}
