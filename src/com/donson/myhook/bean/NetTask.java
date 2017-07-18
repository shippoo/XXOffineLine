package com.donson.myhook.bean;

public class NetTask {
	String packageName;
	String planId;
	int scriptTime;
	String retained;
	int execNum;
	int execTotal;
	String orderId;
	int isUseVpn ;
	int isRetainVPN;
	String scrVer;
	String apkVer;
	int taskId;
	int isPause;
	String apkPath;
	String luaPath;
	String channel;
	int planType;
	String fileLuaName;
	int wifiRate;
	int simRate;
	int vpnId;
	int updateFlag;
	String downIp;
	int deadlineTime;
	int fileFlag;
	String model;
	int vpnType = 1;//1无极；2系统 ；3无极独立版
	String commonLuaVer;
//	String wjUserName;
//	String wjPass;
	
	
	
	public int getVpnType() {
		return vpnType;
	}
	public String getCommonLuaVer() {
		return commonLuaVer;
	}
	public void setCommonLuaVer(String commonLuaVer) {
		this.commonLuaVer = commonLuaVer;
	}
	public void setVpnType(int vpnType) {
		this.vpnType = vpnType;
	}
//	public String getWjUserName() {
//		return wjUserName;
//	}
//	public void setWjUserName(String wjUserName) {
//		this.wjUserName = wjUserName;
//	}
//	public String getWjPass() {
//		return wjPass;
//	}
//	public void setWjPass(String wjPass) {
//		this.wjPass = wjPass;
//	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getDeadlineTime() {
		return deadlineTime;
	}
	public void setDeadlineTime(int deadlineTime) {
		this.deadlineTime = deadlineTime;
	}
	public int getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(int fileFlag) {
		this.fileFlag = fileFlag;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getScriptTime() {
		return scriptTime;
	}
	public void setScriptTime(int scriptTime) {
		this.scriptTime = scriptTime;
	}
	public String getRetained() {
		return retained;
	}
	public void setRetained(String retained) {
		this.retained = retained;
	}
	public int getExecNum() {
		return execNum;
	}
	public void setExecNum(int execNum) {
		this.execNum = execNum;
	}
	public int getExecTotal() {
		return execTotal;
	}
	public void setExecTotal(int execTotal) {
		this.execTotal = execTotal;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getIsUseVpn() {
		return isUseVpn;
	}
	public void setIsUseVpn(int isUseVpn) {
		this.isUseVpn = isUseVpn;
	}
	
	public int getIsRetainVPN() {
		return isRetainVPN;
	}
	public void setIsRetainVPN(int isRetainVPN) {
		this.isRetainVPN = isRetainVPN;
	}
	public String getScrVer() {
		return scrVer;
	}
	public void setScrVer(String scrVer) {
		this.scrVer = scrVer;
	}
	public String getApkVer() {
		return apkVer;
	}
	public void setApkVer(String apkVer) {
		this.apkVer = apkVer;
	}
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public int getIsPause() {
		return isPause;
	}
	public void setIsPause(int isPause) {
		this.isPause = isPause;
	}
	public String getApkPath() {
		return apkPath;
	}
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	public String getLuaPath() {
		return luaPath;
	}
	public void setLuaPath(String luaPath) {
		this.luaPath = luaPath;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public int getPlanType() {
		return planType;
	}
	public void setPlanType(int planType) {
		this.planType = planType;
	}
	public String getFileLuaName() {
		return fileLuaName;
	}
	public void setFileLuaName(String fileLuaName) {
		this.fileLuaName = fileLuaName;
	}
	
	public int getWifiRate() {
		return wifiRate;
	}
	public void setWifiRate(int wifiRate) {
		this.wifiRate = wifiRate;
	}
	public int getSimRate() {
		return simRate;
	}
	public void setSimRate(int simRate) {
		this.simRate = simRate;
	}
	
	public int getVpnId() {
		return vpnId;
	}
	public void setVpnId(int vpnId) {
		this.vpnId = vpnId;
	}
	
	public int getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}
	
	public String getDownIp() {
		return downIp;
	}
	public void setDownIp(String downIp) {
		this.downIp = downIp;
	}
	@Override
	public String toString() {
		return "NetTask [packageName=" + packageName + ", planId=" + planId
				+ ", scriptTime=" + scriptTime + ", retained=" + retained
				+ ", execNum=" + execNum + ", execTotal=" + execTotal
				+ ", orderId=" + orderId + ", isUseVpn=" + isUseVpn
				+ ", isRetainVPN=" + isRetainVPN + ", scrVer=" + scrVer
				+ ", apkVer=" + apkVer + ", taskId=" + taskId + ", isPause="
				+ isPause + ", apkPath=" + apkPath + ", luaPath=" + luaPath
				+ ", channel=" + channel + ", planType=" + planType
				+ ", fileLuaName=" + fileLuaName + ", wifiRate=" + wifiRate
				+ ", simRate=" + simRate + ", vpnId=" + vpnId + ", updateFlag="
				+ updateFlag + ", downIp=" + downIp + ", deadlineTime="
				+ deadlineTime + ", fileFlag=" + fileFlag + ", model=" + model
				+ ", vpnType=" + vpnType + "]";
	}
	
	
	
	
}
