package com.donson.myhook.bean;

public class WifiMode2 {
	/**
	 * SSID/���������
	 */
	public String ssid;
	/**
	 * BSSID/������ַ
	 */
	public String wifilist;
	public String bssid;
	public String macAddress;
	public int linkSpeed;
	/**
	 * Rssi/��������Ľ����ź�ǿ��
	 */
	public int rssi;
	
	public int getLinkSpeed() {
		return linkSpeed;
	}
	public void setLinkSpeed(int linkSpeed) {
		this.linkSpeed = linkSpeed;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getWifilist() {
		return wifilist;
	}
	public void setWifilist(String wifilist) {
		this.wifilist = wifilist;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	@Override
	public String toString() {
		return "WifiMode2 [ssid=" + ssid + ", wifilist=" + wifilist
				+ ", bssid=" + bssid + ", macAddress=" + macAddress
				+ ", linkSpeed=" + linkSpeed + ", rssi=" + rssi + "]";
	}
	
}
