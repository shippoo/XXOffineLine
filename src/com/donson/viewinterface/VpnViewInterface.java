package com.donson.viewinterface;

public interface VpnViewInterface {
	public void toast(String tips);
	public void toastBig(String tips);
	public boolean getCheckBox();
	public void setCheckBox(boolean value);
	public String getReConnText();
	public void setReConnText(boolean err,String time);
	public String getConnText();
	public void setConnText(boolean err,String time);
	
	
	public String getServer();
	public void setServer(boolean err,String server);
	public String getUserName();
	public void setUserName(boolean err,String userName);
	public String getPassword();
	public void setPassword(boolean err,String password);
	
	
	public void setSystemVpnShow();
	public void setWJVpnShow();
	
	public void clickTochoseSystemVpn();
	public void clickTochoseWjVpn();
	
	public boolean isSettingSystemVpn();
	
	public String getWjUserName();
	public String getWjPassword();
	
	public void setWJUserName(String userName);
	public void setWJPassward(String password);
	
	public void setWUJIVpnVersion(String version);
}
